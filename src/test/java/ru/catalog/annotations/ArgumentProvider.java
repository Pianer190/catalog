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
            return Stream.of(this.parseValueArray()).map(Arguments::of);
        }
        User[] users = usersAnnotation.value();

        if (this.annotation.value().length < 1) {
            return Stream.of(users).map(Arguments::of);
        }

        return Arrays.stream(users)
                .flatMap(item1 -> Arrays.stream(this.parseValueArray()).map(item2 -> Arguments.of(item1, item2)));
    }

    private Object[] parseValueArray() {
        boolean useHeadersInDisplayName = this.annotation.useHeadersInDisplayName();
        List<Object> argumentsList = new ArrayList();

        try {
            AtomicInteger index = new AtomicInteger(0);
            String[] var5 = this.annotation.value();
            int var6 = var5.length;

            for (String input : var5) {
                index.incrementAndGet();
                String[] csvRecord = this.csvParser.parseLine(input + "\n");

                Preconditions.notNull(csvRecord, () -> "Record at index " + index + " contains invalid CSV: \"" + input + "\"");
                argumentsList.add(csvRecord);
            }
        } catch (Throwable var10) {
            throw handleCsvException(var10, this.annotation);
        }

        return argumentsList.toArray();
    }

    static String[] getHeaders(CsvParser csvParser) {
        return Arrays.stream(csvParser.getContext().parsedHeaders()).map(String::trim).toArray(String[]::new);
    }

    static Arguments processCsvRecord(Object[] csvRecord, Set<String> nullValues, boolean useHeadersInDisplayName, String[] headers) {
        if (nullValues.isEmpty() && !useHeadersInDisplayName) {
            return Arguments.of(csvRecord);
        } else {
            Preconditions.condition(!useHeadersInDisplayName || csvRecord.length <= headers.length, () -> String.format("The number of columns (%d) exceeds the number of supplied headers (%d) in CSV record: %s", csvRecord.length, headers.length, Arrays.toString(csvRecord)));
            Object[] arguments = new Object[csvRecord.length];

            for(int i = 0; i < csvRecord.length; ++i) {
                Object column = csvRecord[i];
                if (nullValues.contains(column)) {
                    column = null;
                }

                if (useHeadersInDisplayName) {
                    column = Named.of(headers[i] + " = " + column, column);
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
