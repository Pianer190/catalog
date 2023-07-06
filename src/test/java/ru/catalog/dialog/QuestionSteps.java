package ru.catalog.dialog;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;

/** Класс-потомок для подтверждения диалогового окна с вопросом
 * Шаги взаимодействия:
 * <blockquote><pre>
 *     Step YES - Нажатие на кнопку да
 * </pre></blockquote>
 * Содержит в себе тип диалогового окна:
 *  * <blockquote><pre>
 *  *     Type QUESTION  - Тип диалогового окна вопрос {@link Type}
 *  * </blockquote></pre>
 * */
public class QuestionSteps extends DialogSteps {
    public QuestionSteps(String message) {
        super(message, Type.QUESTION);
    }

    /** Нажатие на кнопку да */
    @Step("Нажатие 'Да'")
    public void yes() {
        object.yes.should(visible).click();
    }

    @Step("Нажатие 'Нет'")
    public void no() {
        object.no.should(visible).click();
    }
}