package ru.catalog.junit.administration.security.users;

import io.qameta.allure.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.catalog.annotations.Structure;
import ru.catalog.annotations.User;
import ru.catalog.junit.BaseTest;


@Structure({"Администрирование", "Безопасность", "Пользователи"})
class PageTest extends BaseTest<PageSteps> {

    @User(login = "spoadmin", password = "12345678")
    @Issue("CATALOG22-96")
    @DisplayName("Тест открытия ЭФ Пользователи")
    @Test
    void testOpen() {
        project.checkOpen();
    }
}
