package ru.catalog.junit;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

// Администрирование
class Administration {
    Administration() {
        $x("//*[text() = 'Администрирование']/ancestor-or-self::a").should(visible).click();
    }

    // Меню
    static class Menu extends Administration {
        Menu() {
            $x("//*[text() = 'Меню']/ancestor-or-self::a").should(visible).click();
        }
    }
}