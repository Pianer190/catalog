package ru.catalog.extensions;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;


// Получение названия ЭФ перед каждым АТ
public class BeforeEachExtension implements BeforeEachCallback {
    private static final Logger LOG = LoggerFactory.getLogger(BeforeEachExtension.class);
    public static String form_name;

    @Override
    public void beforeEach(@Nonnull ExtensionContext extensionContext) {
        form_name = extensionContext.getRequiredTestClass().getAnnotation(Feature.class).value();
        LOG.debug("Запуск теста на ЭФ '" + form_name + "'");
    }
}
