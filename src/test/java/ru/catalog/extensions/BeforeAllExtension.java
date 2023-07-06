package ru.catalog.extensions;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.LoggerFactory;
import ru.rbt.Props;
import org.openqa.selenium.Proxy;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

// Расширение для настройки браузера
public class BeforeAllExtension implements BeforeAllCallback {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BeforeAllExtension.class);
    private static final Lock LOCK = new ReentrantLock();

    @Override
    public void beforeAll(@Nonnull final ExtensionContext context) {
        // Блокировка доступа всем потокам, кроме первого
        LOCK.lock();
        try {
            String uniqueKey = this.getClass().getName();
            Object value = context.getRoot().getStore(GLOBAL).get(uniqueKey);
            if (value == null) {
                context.getRoot().getStore(GLOBAL).put(uniqueKey, this);
                configureBrowser();
            }
        } finally {
            // Разблокировка
            LOCK.unlock();
        }
    }

    // Настройка браузера
    public void configureBrowser() {
        // Заглушка информационных сообщений от selenium.remote
        LOG.debug("Началась настройка браузера");
        Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);

        // Настройки селеноида
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableLog", true);
        selenoidOptions.put("timeZone", "Europe/Moscow");
        capabilities.setCapability("selenoid:options", selenoidOptions);

        // Установка прокси
        Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setHttpProxy(Props.get("proxy.host") + ":" + Props.get("proxy.port"));
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        // Конфигурация браузера
        Configuration.driverManagerEnabled = false;
        Configuration.remote  = Props.get("webdriver.url");
        Configuration.browser = Props.get("webdriver.browser.name");
        Configuration.baseUrl = "about:blank";
        Configuration.timeout = 20000;
        Configuration.browserCapabilities = capabilities;

        // Добавление Allure слушателя
        SelenideLogger.addListener("allure", new AllureSelenide());
        LOG.debug("Настройка браузера завершена\n\n");
    }
}