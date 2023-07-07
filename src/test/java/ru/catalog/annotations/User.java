package ru.catalog.annotations;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Test
public @interface User {
    String login();
    String password();
}
