package ru.catalog.junit.administration.menu;

import org.junit.jupiter.params.ParameterizedTest;
import ru.catalog.annotations.*;
import ru.catalog.junit.*;



@Structure({"Администрирование", "Меню"})
class PageTest extends BaseTest<PageSteps> {

    @User(login = "spoadmin", password = "12345678")
    @CsvSource({
            "test1,test2",
            "test3,test4"
    })
    @ParameterizedTest
    void testOpen(String test1, String test2) {
    }
}
