package ru.catalog.junit.administration.security.users;

import ru.catalog.junit.BaseSteps;
import ru.catalog.junit.administration.menu.PageObject;




public class PageSteps extends BaseSteps<PageObject> {

    PageSteps test() {
        object.preloader.click();
        return this;


    }
}