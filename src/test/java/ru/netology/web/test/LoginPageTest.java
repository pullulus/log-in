package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class LoginPageTest {

    @Test
    void shouldLogInValidUser() {
        val activeValidUser = DataHelper.registerActiveValidUser();
        open("http://localhost:9999");
        $("[name= 'login']").setValue(activeValidUser.getLogin());
        $("[name= 'password']").setValue(activeValidUser.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".heading").shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInBlockedUser() {
        val blockedUser = DataHelper.registerBlockedUser();
        open("http://localhost:9999");
        $("[name= 'login']").setValue(blockedUser.getLogin());
        $("[name= 'password']").setValue(blockedUser.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInUserNonExists() {
        val userNonExists = DataHelper.registerUserNonExists();
        open("http://localhost:9999");
        $("[name= 'login']").setValue(userNonExists.getLogin());
        $("[name= 'password']").setValue(userNonExists.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInUserWithNonValidLogin() {
        val userWithValidLogin = DataHelper.registerValidAndNonValidLogin();
        val userWithNonValidLogin = DataHelper.registerValidAndNonValidLogin();
        open("http://localhost:9999");
        $("[name= 'login']").setValue(userWithNonValidLogin.getLogin());
        $("[name= 'password']").setValue(userWithValidLogin.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNonLogInUserWithNonValidPassword() {
        val userWithValidPassword = DataHelper.registerValidAndNonValidPassword();
        val userWithNonValidPassword = DataHelper.registerValidAndNonValidPassword();
        open("http://localhost:9999");
        $("[name= 'login']").setValue(userWithValidPassword.getLogin());
        $("[name= 'password']").setValue(userWithNonValidPassword.getPassword());
        $("[data-test-id= 'action-login']").click();
        $(".notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}
