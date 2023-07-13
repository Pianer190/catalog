package ru.catalog.junit.administration.security.users;

import io.qameta.allure.Step;
import ru.catalog.junit.BaseSteps;

import static com.codeborne.selenide.Condition.*;


class PageSteps extends BaseSteps<PageObject> {

    @Step("Проверка открытия ЭФ")
    PageSteps checkOpen() {
        object.form_header.should(visible);
        object.cancel_btn.should(visible);
        object.delete_btn.should(visible);
        object.add_btn.should(visible);
        object.refresh_btn.should(visible);
        object.save_btn.should(visible);
        object.column_user_locked.should(visible);
        object.profile_column.should(visible);
        object.last_name_column.should(visible);
        object.column_name.should(visible);
        object.patronymic_column.should(visible);
        object.organization_column.should(visible);
        object.username_column.should(visible);
        object.password_column.should(visible);
        object.add_label_btn.should(visible);
        object.collapse_btn.should(visible);
        object.expand_btn.should(visible);
        object.close_btn.should(visible);

        return this;
    }
}