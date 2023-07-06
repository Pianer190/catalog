package ru.catalog.cell;

/**
 * Класс содержит в себе типы ячейки таблицы
 * <p>
 * Типы инициализации в ячейке:
 * <blockquote><pre>
 *     CellType TEXT - Поле с типом Текст
 *     CellType VALUE - Поле с цифровым типом
 *     CellType COMBOBOX - Поле с типом комбобокс
 *     CellType DATE - Поле с типом дата
 * </pre></blockquote>
 */

public enum CellType {
    TEXT,
    VALUE,
    COMBOBOX,
    DATE
}