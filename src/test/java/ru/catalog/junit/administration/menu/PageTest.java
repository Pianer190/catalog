package ru.catalog.junit.administration.menu;

import org.junit.jupiter.api.Test;
import ru.catalog.annotations.*;
import ru.catalog.junit.*;

import static com.codeborne.selenide.Selenide.sleep;


@Structure({"Администрирование", "Меню"})
class PageTest extends BaseTest<PageSteps> {

    @Uri("/catalog/develop/admin")
    @User(login = "spoadmin", password = "12345678")
    @Test
    void testOpen() {

    }
}
