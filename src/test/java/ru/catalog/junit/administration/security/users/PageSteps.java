package ru.catalog.junit.administration.security.users;

import io.qameta.allure.Step;
import ru.catalog.junit.BaseSteps;

import static com.codeborne.selenide.Condition.*;


class PageSteps extends BaseSteps<PageObject> {

    @Step("Проверка открытия ЭФ")
    PageSteps checkOpen() {
        object.form_header.should(visible);
        //TODO Добавить проверки на все основные элементы
        return this;
    }
}