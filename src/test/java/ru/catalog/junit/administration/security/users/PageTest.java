package ru.catalog.junit.administration.security.users;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.catalog.annotations.Structure;
import ru.catalog.annotations.User;
import ru.catalog.junit.BaseTest;


@Epic("Администрирование")
@Feature("Безопасность")
@Structure({"Администрирование", "Безопасность", "Пользователи"})
@DisplayName("ЭФ Пользователи")
@User(login = "spoadmin", password = "12345678")
class PageTest extends BaseTest<PageSteps> {

    @Test
    @Story("Пользователи")
    @Issue("CATALOG22-96")
    @DisplayName("Тест открытия ЭФ")
    void testOpen() {
        project.checkOpen();
    }
}
