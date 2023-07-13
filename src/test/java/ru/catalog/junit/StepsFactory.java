package ru.catalog.junit;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import ru.rbt.Props;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.catalog.extensions.BeforeEachExtension.*;
import static ru.catalog.extensions.BeforeEachExtension.structure;
import static ru.catalog.junit.BaseSteps.LOG;
import static ru.rbt.UrlParser.authFormat;


class StepsFactory {

    public static String session_id = "";

    // Открытие ЭФ и инициализация объекта шагов
    @CheckReturnValue
    @Nonnull
    static <PageStepsClass> PageStepsClass openForm(Class<PageStepsClass> pageStepsClass) {
        return stepsFactory(pageStepsClass);
    }

    @SafeVarargs
    @CheckReturnValue
    @Nonnull
    static <PageStepsClass> PageStepsClass openForm(PageStepsClass... reified) {
        if (reified.length > 0) {
            throw new IllegalArgumentException("Пожалуйста, не передавайте в данную функцию никаких значений");
        } else {
            return (PageStepsClass) stepsFactory(reified.getClass().getComponentType());
        }
    }

    @CheckReturnValue
    @Nonnull
    static <PageStepsClass, T extends PageStepsClass> PageStepsClass openForm(T pageSteps) {
        return stepsFactory(pageSteps);
    }

    @CheckReturnValue
    @Nonnull
    private static <PageStepsClass> PageStepsClass stepsFactory(Class<PageStepsClass> pageStepsClass) {
        try {
            Constructor<PageStepsClass> constructor = pageStepsClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return stepsFactory(constructor.newInstance());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Не удалось инициализировать класс шагов: " + pageStepsClass, e);
        }
    }

    @CheckReturnValue
    @Nonnull
    private static <PageStepsClass, T extends PageStepsClass> PageStepsClass stepsFactory(T pageSteps) {
        session_id = "";
        LOG = LoggerFactory.getLogger(pageSteps.getClass());

        // Получение номера модуля из имени класса
        String auth_path   = "/?qaauth=" + authFormat(user.login(), user.password());
        String url         = Props.get("project.url") + auth_path;

        Allure.ThrowableRunnableVoid open = () -> {
            LOG.debug("Запуск сессии");

            // Трижды пытаемся запустить сессию
            int retries = 3;
            while (retries > 0) {
                try {
                    open(url);
                    break;
                } catch (SessionNotCreatedException e) {
                    retries--;
                    if (retries == 0) {
                        throw e;
                    }
                }
            }

            // Добавляем дополнительную информацию о сессии в отчёт
            RemoteWebDriver driver = (RemoteWebDriver) WebDriverRunner.getWebDriver();
            session_id = driver.getSessionId().toString();
            LOG.debug("Запущена сессия " + session_id);

            Allure.parameter("URL",       Props.get("project.url"));
            Allure.parameter("Логин",     user.login());
            Allure.parameter("Пароль",    user.password());
            Allure.parameter("ЭФ",        Arrays.toString(structure));
            Allure.parameter("ID Сессии", session_id);

            // Делаем окно во весь экран
            driver.manage().window().maximize();

            // Принимаем информацию
            StartInfoObject info = page(StartInfoObject.class);
            info.window.should(visible);
            info.box.click();
            info.start.click();
            info.window.should(not(exist));

            // Открытие ЭФ
            openForm(structure);
        };

        Allure.step("Открытие ЭФ " + Arrays.toString(structure), open);

        LOG.debug("Выполнение тестов...");

        return pageSteps;
    }

    static private void openForm(String[] structure) {
        Find<SelenideElement> header_item = (String name) ->
                $x("//*[contains(@id, 'common-ux-desktop-menu') and contains(@class, 'x-toolbar-default')]//*[text()='" + name + "']/ancestor-or-self::a")
                        .should(visible);

        Find<SelenideElement> menu_item = (String name) ->
                $x("//*[contains(@id, 'menu') and contains(@class, 'x-menu-default')]//*[contains(@id, 'menuitem') and contains(@class, 'x-menu-item-default') and descendant-or-self::*[text()='" + name + "' and ancestor-or-self::a]]")
                        .should(visible);

        Show menu = (Find<SelenideElement> element, String name) -> {
            String id = element.find(name).should(visible).getAttribute("id");
            executeJavaScript("Ext.getCmp(arguments[0]).menu.show();", id);
        };

        if (structure.length == 1) {
            header_item.find(structure[0]).click();
        } else if (structure.length == 2) {
            menu.show(header_item, structure[0]);
            menu_item.find(structure[1]).click();
        } else if (structure.length == 3) {
            menu.show(header_item, structure[0]);
            menu.show(menu_item, structure[1]);
            menu_item.find(structure[2]).click();
        }
    }
}

@FunctionalInterface
interface Show {
    void show(Find<SelenideElement> element, String name);
}

@FunctionalInterface
interface Find<T> {
    T find(String name);
}