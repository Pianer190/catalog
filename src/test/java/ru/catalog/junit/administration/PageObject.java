package ru.catalog.junit.administration;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/** Класс предоставляет возможность взаимодействовать с элементами экранной формы "Формирование ГПВ" с помощью xpath
 * <p>
 * Класс поделен на разные классы экранной формы, к которой происходит подключение в тесте, что помогает нам разделить эф (Может быть уточнить что-то)
 * <p>
 * Объект взаимодействия:
 * <blockquote><pre>
 *     SelenideElement наименование объекта взаимодействия
 * </pre></blockquote>
 * Содержащиеся классы в ЭФ:
 * <blockquote><pre>
 *     class PageObject - класс для взаимодействия с главной ЭФ
 *     class FilterObject - класс для взаимодействия с параметрами фильтрации
 * </pre></blockquote>
 * */
class PageObject {

	@FindBy(xpath = "//*[text() = 'Администрирование']")
	SelenideElement test;
}