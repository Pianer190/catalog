package ru.catalog.junit;

import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.*;
import static ru.rbt.ReflectionUtils.getGenericClass;

// Базовый метод для модальных окон (необходима параметризация с указанием на используемый класс Page)
abstract public class BaseSteps<PageObject> {

    public static Logger LOG;

    protected final PageObject object;
    {
        // Инициализация переменной
        Class<PageObject> pageObjectClass = getGenericClass(this.getClass(), BaseSteps.class);
        if (pageObjectClass != null) {
            object = page(pageObjectClass);
        } else {
            object = (PageObject) page(BaseObject.class);
        }
    }
}
