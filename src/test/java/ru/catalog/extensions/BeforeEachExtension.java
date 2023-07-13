package ru.catalog.extensions;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.AnnotationConsumerInitializer;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.ExceptionUtils;
import org.junit.platform.commons.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catalog.annotations.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


// Получение названия ЭФ и пользователя перед каждым АТ
public class BeforeEachExtension implements BeforeEachCallback {
    private static final Logger LOG = LoggerFactory.getLogger(BeforeEachExtension.class);
    public static String[] structure;
    public static User user;

    @Override
    public void beforeEach(@Nonnull ExtensionContext extensionContext) {
        user      = getCurrentUser(extensionContext);
        structure = extensionContext.getRequiredTestClass().getAnnotation(Structure.class).value();

        LOG.debug("Запуск теста на ЭФ '" + String.join(" -> ", structure) + "' (Логин: '" + user.login() + "', Пароль: '" + user.password() + "')");
    }

    private User getCurrentUser(ExtensionContext extensionContext) {
        Method method = extensionContext.getRequiredTestMethod();
        Class<?>  clazz  = extensionContext.getRequiredTestClass();
        if (AnnotationUtils.isAnnotated(method, User.class)) {
            return method.getAnnotation(User.class);
        } else if (AnnotationUtils.isAnnotated(method, Users.class)) {
            Object[] args = getCurrentArgs(extensionContext);
            for (Object arg : args) {
                try {
                    return (User)arg;
                } catch (ClassCastException ignored) {}
            }
        } else if (AnnotationUtils.isAnnotated(clazz, User.class)) {
            return clazz.getAnnotation(User.class);
        } else {
            throw new RuntimeException("The test method must contain an annotation like User or Users");
        }
        return null;
    }

    private Object[] getCurrentArgs(ExtensionContext extensionContext) {
        Method method = extensionContext.getRequiredTestMethod();
        AtomicLong invocationCount = new AtomicLong(0L);
        List<Object[]> all_args = AnnotationUtils.findRepeatableAnnotations(method, ArgumentsSource.class).stream().map(ArgumentsSource::value).map(ReflectionUtils::newInstance).map((provider) -> AnnotationConsumerInitializer.initialize(method, provider)).flatMap((provider) -> {
            try {
                return provider.provideArguments(extensionContext);
            } catch (Exception e) {
                throw ExceptionUtils.throwAsUncheckedException(e);
            }
        }).map(Arguments::get).peek(arguments -> invocationCount.incrementAndGet()).toList();

        return all_args.get(getCurrentIndex(extensionContext));
    }

    private int getCurrentIndex(ExtensionContext extensionContext) {
        String displayName = extensionContext.getDisplayName();
        String[] parts = displayName.split("\\[");
        if (parts.length > 1) {
            String numberString = parts[1].split("]")[0];
            return Integer.parseInt(numberString) - 1;
        }
        return -1;
    }
}
