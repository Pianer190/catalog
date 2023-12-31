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
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'headercontainer') and contains(@class, 'x-grid-header-ct-default')]//*[contains(@id,'checkcolumn') and contains(@class, 'x-column-header-default')]//*[text() = 'Пользователь заблокирован']/ancestor-or-self::*[contains(@id,'checkcolumn') and contains(@class,'x-column-header-inner-empty')]")
    SelenideElement column_user_locked;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'headercontainer') and contains(@class, 'x-grid-header-ct-default')]//*[contains(@id, 'common-pickercolumn') and contains(@class, 'x-column-header-default')]//*[contains(@id, 'common-pickercolumn') and contains(@class, 'x-column-header-text-container')]//*[text() = 'Профиль']/ancestor-or-self::*[contains(@id, 'common-pickercolumn') and contains(@class, 'x-leaf-column-header')]")
    SelenideElement profile_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'headercontainer') and contains(@class, 'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class, 'x-column-header-text-container')]//*[text() = 'Фамилия']/ancestor-or-self::*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-inner-empty')]")
    SelenideElement last_name_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'headercontainer') and contains(@class, 'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class, 'x-column-header-text-container')]//*[text() = 'Имя']/ancestor-or-self::*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-inner-empty')]")
    SelenideElement column_name;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'headercontainer') and contains(@class, 'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class, 'x-column-header-text-container')]//*[text() = 'Отчество']/ancestor-or-self::*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-inner-empty')]")
    SelenideElement patronymic_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'headercontainer') and contains(@class, 'x-grid-header-ct-default')]//*[contains(@id,'common-pickercolumn') and contains(@class, 'x-column-header-text')]//*[text() = 'Организация']/ancestor-or-self::*[contains(@id,'pickercolumn') and contains(@class,'x-column-header-inner-empty')]")

    SelenideElement organization_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'headercontainer') and contains(@class, 'x-grid-header-ct-default')]//*[contains(@id,'gridcolumn') and contains(@class, 'x-column-header-text-container')]//*[text() = 'Имя пользователя']/ancestor-or-self::*[contains(@id,'gridcolumn') and contains(@class,'x-column-header-inner-empty')]")

    SelenideElement username_column;
    @FindBy(xpath = "//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-default-closable')]//*[contains(@id, 'common-ux-desktop-window') and contains(@class, 'x-window-body-default')]//*[contains(@id, 'templatecolumn') and contains(@class, 'x-column-header-default')]//*[contains(@id, 'templatecolumn') and contains(@class, 'x-column-header-text-container')]//*[text() =  'Пароль']/ancestor-or-self::*[contains(@id,'templatecolumn') and contains(@class,'x-leaf-column-header')]")

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