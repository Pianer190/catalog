package ru.catalog.extensions;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.catalog.junit.*;

import javax.annotation.Nonnull;


// Получение названия ЭФ перед каждым АТ
public class BeforeEachExtension implements BeforeEachCallback {
    private static final Logger LOG = LoggerFactory.getLogger(BeforeEachExtension.class);
    public static String[] structure;
    public static String form_name;
    public static String login;
    public static String password;
    public static String uri;

    @Override
    public void beforeEach(@Nonnull ExtensionContext extensionContext) {
        uri       = extensionContext.getRequiredTestMethod().getAnnotation(Uri.class).value();
        login     = extensionContext.getRequiredTestMethod().getAnnotation(User.class).login();
        password  = extensionContext.getRequiredTestMethod().getAnnotation(User.class).password();
        structure = extensionContext.getRequiredTestClass() .getAnnotation(Structure.class).value();

        form_name = structure[structure.length - 1];

        LOG.debug("Запуск теста на ЭФ '" + form_name + "' ('" + login + "', '" + password + "')");
    }
}
