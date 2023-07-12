package ru.catalog.junit.administration.security.users;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

class PageObject {

    @FindBy(xpath = "//div[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//div[contains(@id, 'header') and contains(@class,'x-window-header-default')]//div[text() = 'Пользователи']")
    SelenideElement form_header;
    @FindBy(xpath ="//div[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//div[contains(@id, 'gridpanel') and contains(@class,'x-toolbar-default')]//*[contains(@id,'button') and (@data-qtip='Сохранить')]")
    SelenideElement button_save;
    @FindBy(xpath = "//div[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//div[contains(@id, 'gridpanel') and contains(@class,'x-toolbar-default')]//*[contains(@id,'button') and (@data-qtip='Отмена')]")
    SelenideElement button_cancel;
    @FindBy(xpath ="//div[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//div[contains(@id, 'gridpanel') and contains(@class,'x-toolbar-default')]//*[contains(@id,'button') and (@data-qtip='Отмена')]")
    SelenideElement button_add;
    @FindBy(xpath = "//div[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//div[contains(@id, 'gridpanel') and contains(@class,'x-toolbar-default')]//*[contains(@id,'button') and (@data-qtip='Удалить')]")
    SelenideElement button_delete;
    @FindBy(xpath = "//div[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//div[contains(@id, 'gridpanel') and contains(@class,'x-toolbar-default')]//*[contains(@id,'button') and (@data-qtip='Обновить')]")
    SelenideElement button_refresh;
}