package ru.catalog.junit.administration;

import ru.catalog.junit.BaseSteps;

import static com.codeborne.selenide.Condition.*;

/** В данном классе описывается шаги для работы с эф по типу: клика, проверки на существующий элемент и т.д.
 * <p>
 * Класс поделен на разные подклассы экранной формы для удобства работы с определенным модальными формами
 * <p>
 * Аннотации используемые в тестах:
 *  <blockquote><pre>
 *      Step -  Позволяет обозначать ею шаги в нашем тесте
 * </pre></blockquote>
 * Содержащиеся классы в ЭФ:
 * <blockquote><pre>
 *     class PageSteps - класс для взаимодействия с главной ЭФ
 *     class FilterSteps - класс для взаимодействия с параметрами фильтрации
 * </pre></blockquote>
 */

class PageSteps extends BaseSteps<PageObject> {
    PageSteps checkOpen() {
        object.test.should(visible);
        return this;
    }
}