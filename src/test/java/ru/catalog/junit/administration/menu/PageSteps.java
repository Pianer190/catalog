package ru.catalog.junit.administration.menu;

import io.qameta.allure.Step;
import ru.catalog.junit.BaseSteps;


public class PageSteps extends BaseSteps<PageObject> {

    PageSteps test() {
        object.preloader.click();
        return this;


    }
}