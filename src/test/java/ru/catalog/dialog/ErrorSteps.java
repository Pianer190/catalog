package ru.catalog.dialog;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;

/** Класс-потомок для подтверждения диалогового окна с ошибкой
 * Шаги взаимодействия:
 * <blockquote><pre>
 *     Step OK - Нажатие на кнопку окей
 * </pre></blockquote>
 * Содержит в себе тип диалогового окна:
 *  * <blockquote><pre>
 *  *     Type ERROR  - Тип диалогового окна ошибка {@link Type}
 *  * </blockquote></pre>
 * */
public class ErrorSteps extends DialogSteps {
    public ErrorSteps(String message) {
        super(message, Type.ERROR);
    }

    /** Нажатие на кнопку ОК */
    @Step("Нажатие 'ОК'")
    public void ok() {
        object.ok.should(visible).click();
    }
}
