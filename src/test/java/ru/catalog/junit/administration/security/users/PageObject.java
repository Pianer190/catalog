package ru.catalog.junit.administration.security.users;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

class PageObject {

    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class,'x-window-default-resizable')]//*[contains(@id, 'header') and contains(@class,'x-window-header-default')]//*[text() = 'Пользователи']")
    SelenideElement form_header;
    @FindBy(xpath ="//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'gridpanel') and contains(@class, 'x-toolbar-default')]//*[@data-qtip='Сохранить']/ancestor-or-self::a")
    SelenideElement save_btn;
    @FindBy(xpath ="//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'gridpanel') and contains(@class, 'x-toolbar-default')]//*[@data-qtip='Отмена']/ancestor-or-self::a")
    SelenideElement cancel_btn;
    @FindBy(xpath ="//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'gridpanel') and contains(@class, 'x-toolbar-default')]//*[@data-qtip='Добавить']/ancestor-or-self::a")
    SelenideElement add_btn;
    @FindBy(xpath ="//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'gridpanel') and contains(@class, 'x-toolbar-default')]//*[@data-qtip='Удалить']/ancestor-or-self::a")
    SelenideElement delete_btn;
    @FindBy(xpath ="//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'gridpanel') and contains(@class, 'x-toolbar-default')]//*[@data-qtip='Обновить']/ancestor-or-self::a")
    SelenideElement refresh_btn;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'checkcolumn') and contains(@class,'x-column-header-default')]")
    SelenideElement column_user_locked;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'pickercolumn') and contains(@class,'x-column-header-text')//*[text() = 'Профиль']")
    SelenideElement profile_column;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Фамилия')]")
    SelenideElement last_name_column;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Имя')]")
    SelenideElement column_name;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id, 'gridcolumn') and contains(@class, 'x-column-header-text') and text()= 'Отчество']/ancestor-or-self::a")
    SelenideElement patronymic_column;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'common-pickercolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Организация')]")
    SelenideElement organization_column;
    @FindBy(xpath = "//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-default') and (@data-qtip='Имя пользователя')]")
    SelenideElement username_column;
    @FindBy(xpath = "//*[contains(@id, 'headercontainer') and contains(@class,'x-grid-header-ct-default')]//*[contains(@id,'templatecolumn') and contains(@class,'x-column-header-default') ]")
    SelenideElement password_column;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'desktop') and contains(@class,'x-window-header-default')]//*[@data-qtip='Добавить ярлык на рабочий стол']")
    SelenideElement add_label_btn;
    @FindBy(xpath = "//*[contains(@id, '-desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'desktop') and contains(@class,'x-window-header-default')]//*[contains(@id, 'tool') and contains(@class, 'x-tool-img x-tool-minimize')]")
    SelenideElement collapse_btn;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-header-default')]//*[contains(@id, 'tool') and contains(@class, 'x-tool-maximize')]")
    SelenideElement expand_btn;
    @FindBy(xpath = "//*[contains(@id, 'desktop') and contains(@class, 'x-window-default')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class,'x-window-header-default')]//*[contains(@id, 'tool') and contains(@class, 'x-tool-img x-tool-close')]")
    SelenideElement close_btn;
}