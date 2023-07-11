package ru.catalog.junit.administration.security.users;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

class PageObject {
    @FindBy(id = "preloader")
    SelenideElement preloader;

}