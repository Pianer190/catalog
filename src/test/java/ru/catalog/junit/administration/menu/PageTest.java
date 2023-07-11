package ru.catalog.junit.administration.menu;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        System.out.println("test1 = " + test1);
        System.out.println("test2 = " + test2);
    }
}
