package ru.catalog.junit.administration.menu;

import org.junit.jupiter.api.Test;
import ru.catalog.junit.*;


@Structure({"Администрирование", "Меню"})
class PageTest extends BaseTest<PageSteps> {

    @User(login = "spoadmin", password = "12345678")
    @Uri("/catalog/develop/admin")
    @Test
    void testOpen() {
    }
}
