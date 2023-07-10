package ru.catalog.extensions;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catalog.annotations.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;


// Получение названия ЭФ перед каждым АТ
public class BeforeEachExtension implements BeforeEachCallback {
    private static final Logger LOG = LoggerFactory.getLogger(BeforeEachExtension.class);
    public static String[] structure;
    public static String form_name;
    public static User user;
    public static String uri;

    @Override
    public void beforeEach(@Nonnull ExtensionContext extensionContext) {
        Method method = extensionContext.getRequiredTestMethod();
        uri       = method.getAnnotation(Uri.class).value();
        if (method.getAnnotation(User.class) == null) {
            user = method.getAnnotation(Users.class).value()[getUserNumber(extensionContext)];
        } else {
            user = method.getAnnotation(User.class);
        }
        structure = extensionContext.getRequiredTestClass().getAnnotation(Structure.class).value();
        form_name = structure[structure.length - 1];

        LOG.debug("Запуск теста на ЭФ '" + form_name + "' ('" + user.login() + "', '" + user.password() + "')");
    }

    private int getUserNumber(ExtensionContext extensionContext) {
        String displayName = extensionContext.getDisplayName();
        String[] parts = displayName.split("\\[");
        if (parts.length > 1) {
            String numberString = parts[1].split("]")[0];
            return Integer.parseInt(numberString) - 1;
        }
        return -1;
    }
}
