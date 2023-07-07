package ru.catalog.junit.administration.menu;

import ru.catalog.annotations.*;
import ru.catalog.junit.*;


@Structure({"Администрирование", "Меню"})
class PageTest extends BaseTest<PageSteps> {

    @Uri("/catalog/develop/admin")
    @Users({
            @User(login = "usermo", password = "123qweASD"),
            @User(login = "spoadmin", password = "12345678")
    })
    void testOpen() {
    }
}
