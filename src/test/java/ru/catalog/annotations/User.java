package ru.catalog.annotations;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
public @interface User {
    String login();
    String password();
}
