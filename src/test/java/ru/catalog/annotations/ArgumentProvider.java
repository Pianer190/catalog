package ru.catalog.annotations;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.CsvParsingException;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.UnrecoverableExceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
        Method method = context.getRequiredTestMethod();

        if (!AnnotationUtils.isAnnotated(method, Users.class)) {
            return this.parseValueArray();
        }
        User[] users = method.getAnnotation(Users.class).value();

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

            for (String input : this.annotation.value()) {
                index.incrementAndGet();
                Object[] csvRecord = this.csvParser.parseLine(input + "\n");

                Preconditions.notNull(csvRecord, () -> "Record at index " + index + " contains invalid CSV: \"" + input + "\"");
                argumentsList.add(Arguments.of(csvRecord));
            }
        } catch (Throwable e) {
            throw handleCsvException(e, this.annotation);
        }

        return argumentsList.stream();
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
