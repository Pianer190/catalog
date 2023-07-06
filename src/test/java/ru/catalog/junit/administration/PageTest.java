package ru.catalog.junit.administration;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import ru.catalog.junit.BaseTest;


@Feature("Меню")
class PageTest extends BaseTest<PageSteps> {

    @Test
    void testOpen() {
        PageSteps page = project.checkOpen();
        page.checkOpen();
    }
}
