package ru.catalog.dialog;

import io.qameta.allure.Step;
import ru.catalog.junit.BaseSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;


/**
 * Класс - основа для работы с модальными окнами
 * <p>
 * {@link Type} - Допустимые типы диалоговых окнах.
 * Тип задаётся в конструкторах потомков.
 * <p>
 * Конструктор {@link DialogSteps#DialogSteps(String, Type)} принимает 2 параметра:
 * <blockquote><pre>
 *     String message - Сообщение в диалоговом окне
 *     Type   type    - Тип диалогового окна из {@link Type}
 * </blockquote></pre>
 */
abstract class DialogSteps extends BaseSteps<DialogObject> {
    enum Type {
        CONFIRMATION("Подтверждение"),
        QUESTION("Вопрос"),
        INFO("Информация"),
        ERROR("Ошибка");

        private final String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private final Type type;

    DialogSteps(String message, Type type) {
        super();
        this.type = type;
        check(message);
    }

    @Step("Закрытие диалогового окна")
    public void close() {
        object.close.should(visible).click();
    }

    /**
     * Приватный метод, проверяющий корректность сообщение в диалоговом окне
     * и заголовок, который должен соответствовать типу диалогового окна {@link Type}
     * @param message Основное сообщение в диалоговом окне
     */
    @Step("Проверка появления диалогового окна с текстом: '{message}'")
    private void check(String message) {
        object.title  .should(visible).shouldHave(text(this.type.getType()));
        object.message.should(visible).shouldHave(text(message));
    }
}