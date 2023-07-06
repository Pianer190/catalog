package ru.catalog.dialog;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

class DialogObject {
    @FindBy(xpath = "//div[contains(@id, 'messagebox') and contains(@class, 'x-window-default')]//div[contains(@id, 'messagebox') and contains(@class, 'x-window-header-default')]//*[contains(@class, 'x-title-text-default')]")
    SelenideElement title;

    @FindBy(xpath = "//div[contains(@id, 'messagebox') and contains(@class, 'x-window-default')]//div[contains(@class, 'x-window-body-default')]//div[contains(@class, 'x-window-text')]")
    SelenideElement message;

    @FindBy(xpath = "//div[contains(@id, 'messagebox') and contains(@class, 'x-window-default')]//div[contains(@class, 'x-tool-after-title')]")
    SelenideElement close;

    @FindBy(xpath = "//div[contains(@id, 'messagebox') and contains(@class, 'x-window-default')]//div[contains(@id, 'messagebox') and contains(@class, 'x-toolbar-footer')]//*[contains(@id, 'button') and contains(@class, 'x-toolbar-item')]//*[text() = 'Да']/ancestor-or-self::a")
    SelenideElement yes;

    @FindBy(xpath = "//div[contains(@id, 'messagebox') and contains(@class, 'x-window-default')]//div[contains(@id, 'messagebox') and contains(@class, 'x-toolbar-footer')]//*[contains(@id, 'button') and contains(@class, 'x-toolbar-item')]//*[text() = 'Нет']/ancestor-or-self::a")
    SelenideElement no;

    @FindBy(xpath = "//div[contains(@id, 'messagebox') and contains(@class, 'x-window-default')]//div[contains(@id, 'messagebox') and contains(@class, 'x-toolbar-footer')]//*[contains(@id, 'button') and contains(@class, 'x-toolbar-item')]//*[text() = 'OK']/ancestor-or-self::a")
    SelenideElement ok;

    @FindBy(xpath = "//div[contains(@id, 'messagebox') and contains(@class, 'x-window-default')]//div[contains(@id, 'messagebox') and contains(@class, 'x-toolbar-footer')]//*[contains(@id, 'button') and contains(@class, 'x-toolbar-item')]//*[text() = 'Отмена']/ancestor-or-self::a")
    SelenideElement cancel;
}