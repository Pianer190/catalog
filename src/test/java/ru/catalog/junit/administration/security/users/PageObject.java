package ru.catalog.junit.administration.security.users;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

class PageObject {

    @FindBy(xpath = "//div[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//div[contains(@id, 'header') and contains(@class,'x-window-header-default')]//div[text() = 'Пользователи']")
    SelenideElement form_header;

}