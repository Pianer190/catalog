package ru.catalog.dialog;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
/** Класс-потомок для подтверждения диалогового окна с вопросом
 * Шаги взаимодействия:
 * <blockquote><pre>
 *     Step Yes - Нажатие на кнопку согласия
 *     Step No - Нажатие на кнопку отмены
 * </pre></blockquote>
 * Содержит в себе тип диалогового окна:
 *  * <blockquote><pre>
 *  *     Type   CONFIRMATION  - Тип диалогового окна подтверждение {@link Type}
 *  * </blockquote></pre>
 * */

public class ConfirmationSteps extends DialogSteps {
    public ConfirmationSteps(String message) {
        super(message, Type.CONFIRMATION);
    }

    /** Нажатие на кнопку "Да" */
    @Step("Нажатие 'Да'")
    public void yes() {
        object.yes.should(visible).click();
    }

    /** Нажатие на кнопку "Отмена" */
    @Step("Нажатие 'Отмена'")
    public void cancel() {
        object.cancel.should(visible).click();
    }
}
