package ru.catalog.junit;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import ru.rbt.Props;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.catalog.extensions.BeforeEachExtension.form_name;
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
        String auth_path   = "/?qaauth=" + authFormat(Props.get("project.login"), Props.get("project.password"));
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

            Allure.parameter("URL",       url);
            Allure.parameter("Логин",     Props.get("project.login"));
            Allure.parameter("Пароль",    Props.get("project.password"));
            Allure.parameter("ЭФ",        form_name);
            Allure.parameter("ID Сессии", session_id);

            // Делаем окно во весь экран
            driver.manage().window().maximize();

            // Ждём отображения загрузки проекта
            page(BaseObject.class).preloader.should(visible).shouldBe(hidden);

            // Принимаем информацию
            StartInfoObject info = page(StartInfoObject.class);
            info.window.should(visible);
            info.box.click();
            info.start.click();
            info.window.should(not(exist));

            // Открываем ЭФ
            // Проверяем что ЭФ открылась (Есть заголовок)
            // Ждём завершения всех загрузок
        };

        Allure.step("Открытие ЭФ " + form_name, open);

        LOG.debug("Выполнение тестов...");

        return pageSteps;
    }
}
