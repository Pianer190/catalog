package ru.catalog.junit;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

// Базовый класс с общими селекторами
class BaseObject {
}

class StartInfoObject {
    @FindBy(xpath = "//div[contains(@id, 'local-eula') and contains(@class, 'x-window-default') and descendant::*[contains(@id, 'header') and contains(@class, 'x-window-header-default') and descendant::*[text()='Информация']]]")
    SelenideElement window;

    @FindBy(xpath = "//div[contains(@id, 'local-eula') and contains(@class, 'x-window-default')]//div[contains(@id, 'toolbar') and contains(@class, 'x-toolbar-footer')]//div[contains(@id, 'checkboxfield') and contains(@class, 'x-form-item-default')]//*[contains(@id, 'checkboxfield') and contains(@class, 'x-form-cb-label-default') and text() = 'Ознакомлен, подтверждаю']/preceding-sibling::input")
    SelenideElement box;

    @FindBy(xpath = "//div[contains(@id, 'local-eula') and contains(@class, 'x-window-default')]//div[contains(@id, 'toolbar') and contains(@class, 'x-toolbar-footer')]//*[contains(@id, 'button') and text() = 'Начать работу']/ancestor-or-self::a")
    SelenideElement start;
}