package ru.catalog.junit.administration.security.users;

import com.codeborne.selenide.Condition;
import ru.catalog.junit.BaseSteps;
import ru.catalog.junit.administration.menu.PageObject;




public class PageSteps extends BaseSteps<PageObject> {

    PageSteps test() {
        object.preloader.click();
        return this;


    }
    PageSteps checkEF() {
        object.check.should(Condition.visible);
        return this;

    }
}