package ru.catalog.cell;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SetValueMethod;
import com.codeborne.selenide.SetValueOptions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.Keys;
import ru.catalog.junit.BaseSteps;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.catalog.cell.CellType.*;

/**
 * Класс предоставляет возможность взаимодействовать с ячейками таблицы
 * <p>
 * Объекты взаимодействия:
 * <blockquote><pre>
 *     SelenideElement cell   - объект ячейки
 *     SelenideElement editor - объект редактора ячейки
 * </pre></blockquote>
 * <p>
 * Объекты для инициализации:
 * <blockquote><pre>
 *     SelenideElement grid - объект таблицы (задаётся в конструкторе)
 *
 *     Object row    - Строка (задаётся типом <code>String</code> или <code>Integer</code>)
 *     Object column - Колонка (задаётся типом <code>String</code> или <code>Integer</code>)
 * </pre></blockquote>
 * <p>
 * Вспомогательные объекты:
 * <blockquote><pre>
 *     CellType type - Тип ячейки. По умолчанию задаётся как {@link CellType#COMBOBOX}
 *                     Пока зависит только на методику заполнения ячейки {@link CellSteps#fill(Object)}
 * </pre></blockquote>
 */
public class CellSteps extends BaseSteps {
    private SelenideElement cell   = null;
    private SelenideElement editor = null;

    private final SelenideElement grid;

    private Object row;
    private Object column;
    private CellType type = COMBOBOX;

    /** Осуществляется поиск ячейки в полученной таблице по полученным параметрам*/
    public CellSteps(Object row, Object column, SelenideElement grid) {
        this.grid = grid;
        this.row = row;
        this.column = column;

        setCell();
    }

    /** Осуществляется поиск ячейки в выделенной строке таблицы */
    public CellSteps(Object column, SelenideElement grid) {
        this.grid = grid;
        this.column = column;

        setSelectedRow();
    }

    @Step("Переход на строку: {this.row}")
    public CellSteps setRow(Object row) {
        this.row = row;
        setCell();

        return this;
    }

    @Step("Переход на колонку: {this.column}")
    public CellSteps setColumn(Object column) {
        this.column = column;
        setCell();

        return this;
    }

    /** Получение значения из ячейки  */
    @Step("Получение значения из ячейки ({this.row}, {this.column})")
    public String getValue() {
        return cell.getText();
    }

    /** Проверка, что ячейка существует */
    @Step("Проверка наличия ячейки ({this.row}, {this.column})")
    public CellSteps exist() {
        assertTrue(cell.exists(), "Ячейка не существует");
        return this;
    }

    /** Проверка, что ячейка не существует */
    @Step("Проверка отсутствия ячейки ({this.row}, {this.column})")
    public CellSteps notExist() {
        assertFalse(cell.exists(), "Ячейка существует");
        return this;
    }

    /** Проверка, что ячейка изменения */
    @Step("Проверка что ячейка ({this.row}, {this.column}) изменена")
    public CellSteps dirty() {
        assertTrue(Objects.requireNonNull(cell.getAttribute("class")).contains("x-grid-dirty-cell"), "Ячейка не изменена");
        return this;
    }

    /** Проверка, что ячейка не изменения */
    @Step("Проверка что ячейка ({this.row}, {this.column}) не изменена")
    public CellSteps notDirty() {
        assertFalse(Objects.requireNonNull(cell.getAttribute("class")).contains("x-grid-dirty-cell"), "Ячейка изменена");
        return this;
    }

    /** Указание типа ячейки
     * {@link CellType CellType} */
    public CellSteps setType(CellType type) {
        this.type = type;
        return this;
    }

    /** Осуществляет клик по ячейке в таблице */
    @Step("Клик по ячейке ({this.row}, {this.column})")
    public CellSteps click() {
        cell.click();
        // Ожидание загрузок
        return this;
    }

    /** Осуществляет двойной-клик по ячейке в таблице */
    @Step("Двойной клик по ячейке ({this.row}, {this.column})")
    public CellSteps doubleClick() {
        click();
        cell.doubleClick();
        // Ожидание загрузок
        return this;
    }

    /** Осуществляет возможность выбора варианта в ячейке таблице с помощью клавиатуры */
    @Step("Выбор варианта через клавиатуру")
    public SelenideElement getEditor(){
        return this.editor;
    }

    /**
     * Запись значения в ячейку
     * <p>
     * Метод предоставляет возможность заполнить желаемую ячейку указанным значением если у неё имеется редактор.
     * Методика заполнения зависит от указанного типа ячейки
     * <p>
     * По умолчанию: {@link CellType#COMBOBOX}
     */
    @Step("Ввод в ячейку ({this.row}, {this.column}) значения {value}")
    public CellSteps fill(@Nonnull Object value) {
        boolean clear = !getValue().isBlank();

        doubleClick();

        if (editor == null)
            return this;

        if (clear) editor.shouldNotBe(empty);

        switch (type) {
            case COMBOBOX -> {
                editor.setValue(SetValueOptions.withText(value.toString()).usingMethod(SetValueMethod.JS));
                editor.sendKeys(Keys.ARROW_DOWN);
                // Ожидание загрузок
                webdriver().driver().actions().sendKeys(Keys.TAB).perform();
            }
            case TEXT -> {
                editor.setValue(SetValueOptions.withText(value.toString()).usingMethod(SetValueMethod.JS));
                editor.pressTab();
            }
            case VALUE -> editor.setValue(SetValueOptions.withText(value.toString()).usingMethod(SetValueMethod.JS));
            case DATE -> editor.setValue(SetValueOptions.withDate(((LocalDate) value)).usingMethod(SetValueMethod.JS));
        }

        return this;
    }

    /**
     * Поиск ячейки в таблице
     * <p>
     * Метод позволяет найти ячейку в таблице для дальнейшей работы с ней
     * Поиск происходит по строке <code>row</code> и столбцу <code>column</code>
     * Для поиска нужно обязательно найти грид формы
     * <p>
     * Не добавляется в javadoc из-за приватности, возможно нужно пересоздать с приватными настройками
     */
    @Step("Поиск ячейки ({this.row}, {this.column}) в таблице: {this.grid}")
    private void setCell() {
        String grid_id = grid.getAttribute("id");

        String script = "grid = Ext.getCmp(arguments[0]);" +
                "store = grid.getStore();" +
                "vcm = grid.getVisibleColumnManager();" +
                "columns = vcm.columns;";

        switch (column.getClass().getSimpleName()) {
            case "String" -> script += "column = Ext.Array.findBy(columns, col => col.text == arguments[2]);";
            case "Integer" -> script += "column = columns[arguments[2]];";
            case "String[]" ->  script += "column = vcm.headerCt.hideableColumns;" +
                    "for (i = 0; i < arguments[2].length; i++) {" +
                    "   if (i + 1 === arguments[2].length) {" +
                    "       column = Ext.Array.findBy(column, col => col.text == arguments[2][i] && columns.includes(col));" +
                    "       break;" +
                    "   }" +
                    "   column = Ext.Array.findBy(column, col => col.text == arguments[2][i] && col.isGroupHeader === true).items.items;" +
                    "}";
        }

        switch (row.getClass().getSimpleName()) {
            case "String" -> script += "try {" +
                    "field = column.config.tpl.replace(/[{}]/g, '');" +
                    "} catch (TypeError) {" +
                    "field = column.dataIndex;" +
                    "}" +
                    "data = store.getData(); " +
                    "row = data.items.find(row => row.data[field] == arguments[1]);";

            case "Integer" -> script += "row = store.getAt(arguments[1]);";
        }

        script += "view = column.getView();" +
                "context = (new Ext.grid.CellContext(view)).setPosition(row, column);" +
                "view.focusRow(row);" +
                "view.focusCell(context);" +
                "cell = view.getCell(row, column);" +
                "Ext.id(cell, 'ext-element-');" +
                "try {" +
                "editor = column.getEditor().getInputId();" +
                "} catch(TypeError) {" +
                "editor = null;" +
                "}" +
                "var cell_map = {'cell': cell.id, 'editor': editor, 'row': store.getData().items.indexOf(row), 'column': vcm.indexOf(column)};" +
                "return cell_map";

        Map<String, String> cell_map = null;

        try {
            cell_map = executeJavaScript(script, grid_id, row, column);
        } catch (JavascriptException exception) {
            fail("script: " + script, exception);
        }

        assertFalse(Objects.requireNonNull(cell_map).isEmpty(), "Ячейка в колонке: '" + column + "', строка: '" + row + "' не найдена");

        this.cell   = $(By.id(cell_map.get("cell")));
        this.column = cell_map.get("column");
        this.row    = cell_map.get("row");

        String editor_id = cell_map.get("editor");
        if (editor_id != null)
            this.editor = $(By.id(editor_id));
    }

    @Step("Поиск ячейки по колонке {this.column} в выделенной строке таблицы: {this.grid}")
    private void setSelectedRow() {
        String grid_id = grid.getAttribute("id");

        String script = "grid = Ext.getCmp(arguments[0]);" +
                "store = grid.getStore();" +
                "vcm = grid.getVisibleColumnManager();" +
                "columns = vcm.columns;";

        switch (column.getClass().getSimpleName()) {
            case "String" -> script += "column = Ext.Array.findBy(columns, col => col.text == arguments[1]);";
            case "Integer" -> script += "column = columns[arguments[1]];";
            case "String[]" ->  script += "column = vcm.headerCt.hideableColumns;" +
                    "for (i = 0; i < arguments[1].length; i++) {" +
                    "   if (i + 1 === arguments[1].length) {" +
                    "       column = Ext.Array.findBy(column, col => col.text == arguments[1][i] && columns.includes(col));" +
                    "       break;" +
                    "   }" +
                    "   column = Ext.Array.findBy(column, col => col.text == arguments[1][i] && col.isGroupHeader === true).items.items;" +
                    "}";
        }

        script += "row = grid.getSelectionModel().getSelection()[0];" +
                "view = column.getView();" +
                "context = (new Ext.grid.CellContext(view)).setPosition(row, column);" +
                "view.focusRow(row);" +
                "view.focusCell(context);" +
                "cell = view.getCell(row, column);" +
                "Ext.id(cell, 'ext-element-');" +
                "try {" +
                "editor = column.getEditor().getInputId();" +
                "} catch(TypeError) {" +
                "editor = null;" +
                "}" +
                "var cell_map = {'cell': cell.id, 'editor': editor, 'row': store.getData().items.indexOf(row), 'column': vcm.indexOf(column)};" +
                "return cell_map";

        Map<String, String> cell_map = null;

        try {
            cell_map = executeJavaScript(script, grid_id, column);
        } catch (JavascriptException exception) {
            fail("script: " + script, exception);
        }
        assertFalse(Objects.requireNonNull(cell_map).isEmpty(), "Ячейка в колонке: '" + column + "', строка: выделенная не найдена");

        this.cell   = $(By.id(cell_map.get("cell")));
        this.column = cell_map.get("column");
        this.row    = cell_map.get("row");

        String editor_id = cell_map.get("editor");
        if (editor_id != null)
            this.editor = $(By.id(editor_id));
    }
}