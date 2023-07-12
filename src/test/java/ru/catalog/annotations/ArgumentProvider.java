package ru.catalog.annotations;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.CsvParsingException;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.UnrecoverableExceptions;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


class ArgumentProvider implements ArgumentsProvider, AnnotationConsumer<CsvSource> {
    private static final String LINE_SEPARATOR = "\n";
    private CsvSource annotation;
    private CsvParser csvParser;

    ArgumentProvider() {
    }

    @Override
    public void accept(CsvSource annotation) {
        this.annotation = annotation;
        this.csvParser = CsvParserFactory.createParserFor(annotation);
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        boolean textBlockDeclared = !this.annotation.textBlock().isEmpty();
        Users usersAnnotation = context.getRequiredTestMethod().getAnnotation(Users.class);

        if (usersAnnotation == null) {
            return this.parseValueArray();
        }
        User[] users = usersAnnotation.value();

        if (this.annotation.value().length < 1) {
            return Stream.of(users).map(Arguments::of);
        }

        return Stream.of(users)
                .flatMap(user -> this.parseValueArray()
                        .map(arguments -> {
                            Object[] csvRecord = new Object[arguments.get().length + 1];
                            System.arraycopy(arguments.get(), 0, csvRecord, 0, arguments.get().length);
                            csvRecord[csvRecord.length - 1] = user;
                            return Arguments.of(csvRecord);
                        })
                );
    }

    private Stream<Arguments> parseValueArray() {
        List<Arguments> argumentsList = new ArrayList();
        try {
            AtomicInteger index = new AtomicInteger(0);
            String[] var5 = this.annotation.value();
            int var6 = var5.length;

            for (String input : var5) {
                index.incrementAndGet();
                String[] csvRecord = this.csvParser.parseLine(input + "\n");

                Preconditions.notNull(csvRecord, () -> "Record at index " + index + " contains invalid CSV: \"" + input + "\"");
                argumentsList.add(processCsvRecord(csvRecord));
            }
        } catch (Throwable var10) {
            throw handleCsvException(var10, this.annotation);
        }

        return argumentsList.stream();
    }

    static String[] getHeaders(CsvParser csvParser) {
        return Arrays.stream(csvParser.getContext().parsedHeaders()).map(String::trim).toArray(String[]::new);
    }

    static Arguments processCsvRecord(Object[] csvRecord) {
        return Arguments.of(csvRecord);
    }

    static RuntimeException handleCsvException(Throwable throwable, Annotation annotation) {
        UnrecoverableExceptions.rethrowIfUnrecoverable(throwable);
        if (throwable instanceof PreconditionViolationException) {
            throw (PreconditionViolationException) throwable;
        } else {
            throw new CsvParsingException("Failed to parse CSV input configured via " + annotation, throwable);
        }
    }
}
