package ru.catalog.annotations;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.*;
import ru.catalog.extensions.parameterized.ParameterizedTestExtension;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@ArgumentsSource(ArgumentProvider.class)
@ExtendWith({ParameterizedTestExtension.class})
@TestTemplate
@CsvSource
public @interface Users {
    User[] value();

    String name() default "[{index}] {argumentsWithNames}";
}
