package ru.catalog.annotations;

import org.junit.jupiter.api.Named;
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
    private Set<String> nullValues;
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

        return Arrays.stream(users).map(Arguments::of);
                //.flatMap(item1 -> Arrays.stream(this.parseValueArray()).map(item2 -> Arguments.of(item1, item2)));
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
                argumentsList.add(processCsvRecord(csvRecord, this.nullValues));
            }
        } catch (Throwable var10) {
            throw handleCsvException(var10, this.annotation);
        }

        return argumentsList.stream();
    }

    static String[] getHeaders(CsvParser csvParser) {
        return Arrays.stream(csvParser.getContext().parsedHeaders()).map(String::trim).toArray(String[]::new);
    }

    static Arguments processCsvRecord(Object[] csvRecord, Set<String> nullValues) {
        if (nullValues.isEmpty()) {
            return Arguments.of(csvRecord);
        } else {
            Object[] arguments = new Object[csvRecord.length];

            for(int i = 0; i < csvRecord.length; ++i) {
                Object column = csvRecord[i];
                if (nullValues.contains(column)) {
                    column = null;
                }

                arguments[i] = column;
            }

            return Arguments.of(arguments);
        }
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
