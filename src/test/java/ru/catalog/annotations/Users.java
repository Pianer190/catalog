package ru.catalog.annotations;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;
import java.util.stream.Stream;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@ArgumentsSource(Users.UserArgumentProvider.class)
@ParameterizedTest
public @interface Users {
    User[] value();

    class UserArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Users usersAnnotation = context.getRequiredTestMethod().getAnnotation(Users.class);
            User[] users = usersAnnotation.value();
            return Stream.of(users)
                    .map(Arguments::of);
        }
    }
}
