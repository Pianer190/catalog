package ru.catalog.junit.administration.menu;


import ru.catalog.annotations.*;
import ru.catalog.junit.*;


@Structure({"Администрирование", "Меню"})
class PageTest extends BaseTest<PageSteps> {

    @Users({
            @User(login = "spoadmin", password = "12345678"),
            @User(login = "usermo", password = "123qweASD")
    })
    @CsvSource(
            {"test1,test2",
            "test3,test4"}
    )
    void testOpen(String test1, String test2) {
        System.out.println("test1 = " + test1);
        System.out.println("test2 = " + test2);
    }
}
