package ru.catalog.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(ArgumentProvider.class)
public @interface CsvSource {
    String[] value() default {};

    String textBlock() default "";

    boolean useHeadersInDisplayName() default false;

    char quoteCharacter() default '\'';

    char delimiter() default '\u0000';

    String delimiterString() default "";

    String emptyValue() default "";

    int maxCharsPerColumn() default 4096;

    boolean ignoreLeadingAndTrailingWhitespace() default true;
}
