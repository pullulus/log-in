package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class LoginPageTest {

    @BeforeEach
    void shouldOpen() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLogInValidUser() {
        val activeValidUser = DataHelper.registerActiveValidUser();
        $("[name= 'login']").setValue(activeValidUser.getLogin());
        $("[name= 'password']").setValue(activeValidUser.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".heading").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInBlockedUser() {
        val blockedUser = DataHelper.registerBlockedUser();
        $("[name= 'login']").setValue(blockedUser.getLogin());
        $("[name= 'password']").setValue(blockedUser.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInUserNonExists() {
        val userNonExists = DataHelper.registerUserNonExists();
        $("[name= 'login']").setValue(userNonExists.getLogin());
        $("[name= 'password']").setValue(userNonExists.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInUserWithNonValidLogin() {
        val userWithNonValidLogin = DataHelper.registerUserWithNonValidLogin();
        $("[name= 'login']").setValue(userWithNonValidLogin.getLogin());
        $("[name= 'password']").setValue(userWithNonValidLogin.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInUserWithNonValidPassword() {
        val userWithNonValidPassword = DataHelper.registerUserWithNonValidPassword();
        $("[name= 'login']").setValue(userWithNonValidPassword.getLogin());
        $("[name= 'password']").setValue(userWithNonValidPassword.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}
