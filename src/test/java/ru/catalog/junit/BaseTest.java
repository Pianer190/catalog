package ru.catalog.junit;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import ru.catalog.extensions.*;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codeborne.selenide.Selenide.*;
import static ru.catalog.junit.StepsFactory.openForm;
import static ru.catalog.junit.StepsFactory.session_id;
import static ru.rbt.ReflectionUtils.getGenericClass;


@ExtendWith({BeforeAllExtension.class, BeforeEachExtension.class})
public class BaseTest<Steps> {
    protected Steps project;

    @Nonnull
    protected static String currentDateTime() {
        return new SimpleDateFormat("dd_MM_yyyy HH:mm:ss_SSS").format(Calendar.getInstance().getTime());
    }

    // Запуск сессии и авторизация перед каждым АТ
    @BeforeEach
    @Step("Открытие проекта")
    public void setUp() {
        Class<Steps> stepsClass = getGenericClass(this.getClass(), BaseTest.class);
        if (stepsClass != null) {
            project = openForm(stepsClass);
        } else {
            project = openForm();
        }
    }

    // Завершение сессии после каждого АТ
    @AfterEach
    @Step("Завершение сессии")
    public void tearDown() {
        closeWebDriver();
        BaseSteps.LOG.debug("Сессия " + session_id + " завершена\n\n");
    }
}
