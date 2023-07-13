package ru.catalog.junit.administration.security.users;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

class PageObject {

    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-default-resizable')]//*[contains(@id, 'header') and contains(@class,'x-window-header-default')]//*[text() = 'Пользователи']")
    SelenideElement form_header;
    @FindBy(xpath ="//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-gridpanel-toolbar') and contains(@class, 'x-toolbar-default')]//*[contains(@id, 'gridpanel')]//*[contains(@id, 'button') and(@data-qtip='Сохранить')]")
    SelenideElement btn_save;
    @FindBy(xpath ="//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-gridpanel-toolbar') and contains(@class, 'x-toolbar-default')]//*[contains(@id, 'gridpanel')]//*[contains(@id, 'button') and(@data-qtip='Отмена')]")
    SelenideElement btn_cancel;
    @FindBy(xpath ="//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-gridpanel-toolbar') and contains(@class, 'x-toolbar-default')]//*[contains(@id, 'gridpanel')]//*[contains(@id, 'button') and(@data-qtip='Добавить')]")
    SelenideElement btn_add;
    @FindBy(xpath ="//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-gridpanel-toolbar') and contains(@class, 'x-toolbar-default')]//*[contains(@id, 'gridpanel')]//*[contains(@id, 'button') and(@data-qtip='Удалить')]")
    SelenideElement btn_delete;
    @FindBy(xpath ="//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-gridpanel-toolbar') and contains(@class, 'x-toolbar-default')]//*[contains(@id, 'gridpanel')]//*[contains(@id, 'button') and(@data-qtip='Обновить')]")
    SelenideElement btn_refresh;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id, 'checkcolumn') and contains(@class,'x-column-header')]//*[contains(@id,'checkcolumn') and contains(@class,'x-column-header') and text()= 'Пользователь заблокирован']")
    SelenideElement column_user_locked;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'common-pickercolumn-1171') and contains(@class,'x-column-header-default') and (@data-qtip='Профиль')]")
    SelenideElement profile_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Фамилия')]")
    SelenideElement last_name_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Имя')]")
    SelenideElement column_name;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id, 'gridcolumn') and contains(@class, 'x-column-header-text') and text()= 'Отчество']")
    SelenideElement patronymic_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'common-pickercolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Организация')]")
    SelenideElement organization_column;
    @FindBy(xpath = "//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Имя пользователя')]")
    SelenideElement username_column;
    @FindBy(xpath = "//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'templatecolumn') and contains(@class,'x-column-header-default') ]")
    SelenideElement password_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-header-default')]//*[contains(@id, 'tool') and contains(@class, 'x-tool-default')and (@data-qtip='Добавить ярлык на рабочий стол')]")
    SelenideElement add_label_btn;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-header-default')]//*[contains(@id, 'tool') and contains(@class, 'x-tool-img x-tool-minimize')]")
    SelenideElement collapse_btn;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-header-default')]//*[contains(@id, 'tool') and contains(@class, 'x-tool-maximize')]")
    SelenideElement expand_btn;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default x-closable x-window-closable ')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-header-default')]//*[contains(@id, 'tool') and contains(@class, 'x-tool-img x-tool-close')]")
    SelenideElement close_btn;
}