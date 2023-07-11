package ru.catalog.junit.administration.security.users;

import org.junit.jupiter.api.Test;
import ru.catalog.annotations.Structure;
import ru.catalog.annotations.Uri;
import ru.catalog.annotations.User;
import ru.catalog.junit.BaseTest;

import static com.codeborne.selenide.Selenide.sleep;


@Structure({"Администрирование", "Безопасность","Пользователи"})
class PageTest extends BaseTest<ru.catalog.junit.administration.menu.PageSteps> {

    @Uri("/catalog/develop/admin")
    @User(login = "spoadmin", password = "12345678")
    @Test
    void testOpen() {

    }
}
