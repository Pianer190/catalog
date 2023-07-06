package ru.catalog.dialog;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;

/** Класс-потомок для подтверждения диалогового окна с информацией
 * Шаги взаимодействия:
 * <blockquote><pre>
 *     Step OK - Нажатие на кнопку окей
 * </pre></blockquote>
 * Содержит в себе тип диалогового окна:
 *  * <blockquote><pre>
 *  *     Type INFO  - Тип диалогового окна информация {@link Type}
 *  * </blockquote></pre>
 * */
public class InfoSteps extends DialogSteps {
    public InfoSteps(String message) {
        super(message, Type.INFO);
    }

    /** Нажатие на кнопку ОК */
    @Step("Нажатие 'ОК'")
    public void ok() {
        object.ok.should(visible).click();
    }
}