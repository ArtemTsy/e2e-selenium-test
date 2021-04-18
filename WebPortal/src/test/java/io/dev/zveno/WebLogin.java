package io.dev.zveno;

import io.dev.zveno.listeners.AllureListeners;
import io.dev.zveno.texts.NotyTexts;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Listeners({AllureListeners.class})
public class WebLogin extends ParentTest {

    //validate url and wait some content
    //element is present
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование входа в приложение")
    @Description(value = "В этом тесте проверяется наличие всех элементов на странице входа")
    @Story("Тест проверяет наличие элементов входа на странице")
    @Test(priority = 1)
    public void navigateTo(){

        goingLoginPage();

        assertTrue(verifyElementPresent(loginPage.loginCard), "Карточка для входа не представлена на странице");
        assertTrue(verifyElementPresent(loginPage.emailField), "Поле Электронная почта не находится на странице");
        assertTrue(verifyElementPresent(loginPage.passwordField), "Поле Пароль не находится на странице");
        assertTrue(verifyElementPresent(loginPage.submitButton), "Кнопка Войти не находится на странице");
        assertTrue(verifyElementPresent(loginPage.singIn), "Ссылка на регистрацию не находится на странице");
        assertTrue(verifyElementPresent(loginPage.forgotPassword), "Ссыла на востановление пароля не находится на странице");

    }

    //positive test scenario
    //parametrize fields
    @Test(priority = 2)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование входа в приложение")
    @Description("В этом тесте мы проверяем поведение полей при корректном вводе данных")
    @Story("Тест проверяет поведение при корректном вводе данных")
    public void insertAccountFieldCorrectly() {

        loginPage.logIn("correct@zveno.io", "correctPassword");

        assertTrue(verifyElementPresent(loginPage.activeSubmitButton), "Кнопка Войти не активна");
        assertFalse(verifyElementPresent(loginPage.emailValidationMessage), "Сообщение о некорректной валидации электронной почты присутствует");
        assertFalse(verifyElementPresent(loginPage.passwordValidationMessage), "Сообщение о некорректной валидации пароля присутствует");

        loginPage.clearEmailAndPasswordFields();

        loginPage.enterEmail("correct@zveno.io@zveno.io");
        loginPage.enterPassword("correctPassword");
        assertTrue(verifyElementPresent(loginPage.activeSubmitButton), "Кнопка Войти не активна");
        assertFalse(verifyElementPresent(loginPage.emailValidationMessage), "Сообщение о некорректной валидации электронной почты присутствует");
        assertFalse(verifyElementPresent(loginPage.passwordValidationMessage), "Сообщение о некорректной валидации пароля присутствует");

        loginPage.clearEmailAndPasswordFields();
    }

    //negative test scenario
    //parametrize fields
    @Test(priority = 3)
    @Description(value = "Тест проверяет поведение при некорректном вводе данных")
    public void insertAccountFieldIncorrectly(){

        loginPage.clickElement(loginPage.emailField,"Email Field");
        loginPage.clickElement(loginPage.passwordField, "Password Field");
        loginPage.clickElement(loginPage.emailField,"Email Field");
        assertFalse(verifyElementPresent(loginPage.activeSubmitButton), "Кнопка Войти активна");

        assertTrue(verifyElementPresent(loginPage.emailValidationMessage), "Сообщение о некорректной валидации Электронной почты отсутствует");
        assertTrue(verifyElementText(loginPage.emailValidationMessage,"Сообщение о некорректной валидации Электронной почты", "Поле обязательно для заполнения"), "Некорректное сообщение о валидации");

        assertTrue(verifyElementPresent(loginPage.passwordValidationMessage), "Сообщение о некорректной валидации пароля отсутствует");
        assertTrue(loginPage.verifyElementText(loginPage.passwordValidationMessage, "Сообщение о некорректной валидации пароля", "Поле обязательно для заполнения"), "Некорректное сообщение о валидации");

        loginPage.enterPassword("");
        loginPage.enterEmail("qa@zveno.io");
        assertFalse(verifyElementPresent(loginPage.activeSubmitButton), "Кнопка Войти активна");
        assertTrue(verifyElementPresent(loginPage.passwordValidationMessage), "Сообщение о некорректной валидации пароля отсутствует");
        assertTrue(verifyElementText(loginPage.passwordValidationMessage, "Сообщение о некорректной валидации пароля", "Поле обязательно для заполнения"), "Некорректное сообщение о валидации");
        assertFalse(verifyElementPresent(loginPage.emailValidationMessage), "Сообщение о некорректной валидации электронной почты пристутсвует");

        loginPage.clearEmailAndPasswordFields();

        loginPage.enterEmail("");
        loginPage.enterPassword("password");
        assertFalse(verifyElementPresent(loginPage.activeSubmitButton), "Кнопка Войти активна");
        assertFalse(verifyElementPresent(loginPage.passwordValidationMessage), "Сообщение о некорректной валидации пароля присутствует");
        assertTrue(loginPage.verifyElementText(loginPage.emailValidationMessage,"Сообщение о некорректной валидации Электронной почты", "Поле обязательно для заполнения"), "Некорректное сообщение о валидации");

        loginPage.clearEmailAndPasswordFields();

        loginPage.enterEmail("incorrectEmail");
        loginPage.enterPassword("c0rrectP@ssWord");
        assertFalse(verifyElementPresent(loginPage.activeSubmitButton), "Кнопка Войти активна");
        assertFalse(verifyElementPresent(loginPage.passwordValidationMessage), "Сообщение о некорректной валидации пароля присутствует");
        assertTrue(page.verifyElementText(loginPage.emailValidationMessage, "Сообщение о некорректной валидации Электронной почты", "Введите корректный E-mail адрес"), "Некорректное сообщение о валидации");

        loginPage.clearEmailAndPasswordFields();

        //login with limit values



    }

    //positive test scenario
    //wait portal page
    @Test(priority = 4)
    @Description(value = "Тест проверяет вход в систему с активными учетными данными")
    public void loginWithValidCredentials(){

        loginPage.enterEmail(GlobalConfig.USERNAME);
        loginPage.enterPassword(GlobalConfig.PASSWORD);

        loginPage.waitForElement(loginPage.activeSubmitButton, "Submit");
        loginPage.clickElement(loginPage.activeSubmitButton,"Submit");

        loginPage.waitForElement(page.newsTitleItem, "News Title Text");

        assertTrue(loginPage.verifyElementText(page.newsTitleItem, "Добро пожаловать на новый портал Zveno.io!"));

        //user don't unlogged when came back
        GlobalHelper.driver.navigate().back();
        loginPage.waitForElement(page.newsTitleItem, "Title Web Portal");

        assertTrue(loginPage.verifyElementText(page.newsTitleItem, "Добро пожаловать на новый портал Zveno.io!"));

        page.LogOut();

        loginPage.waitForElement(loginPage.loginCard, "Login Card");

       // register sensitivity
        loginPage.enterEmail("QA@zveno.io");
        loginPage.enterPassword(GlobalConfig.PASSWORD);

        loginPage.waitForElement(loginPage.activeSubmitButton, "Submit");
        loginPage.clickElement(loginPage.activeSubmitButton,"Submit");

        loginPage.waitForElement(page.newsTitleItem, "News Title Text");

        assertTrue(loginPage.verifyElementText(page.newsTitleItem, "Добро пожаловать на новый портал Zveno.io!"));

        page.LogOut();

    }

    //negative
    //wait content
    @Test(priority = 5)
    @Description(value = "Тест проверяет вход в систему с неактивными учетными данными")
    public void loginWithInvalidCredentials() {

        loginPage.waitForElement(loginPage.loginCard, "Login Card");

        loginPage.enterEmail("user1@zveno.io");
        loginPage.enterPassword("P@ssw0rd1");

        loginPage.waitForElement(loginPage.activeSubmitButton, "Submit");

        assertTrue(loginPage.clickElement(loginPage.activeSubmitButton, "Submit"), "Пытаемся войти на портал");
        loginPage.waitForElement(loginPage.noty_message, "Invalid message");
        assertTrue(verifyElementPresent(loginPage.noty_message), "Сообщение о неудачном входе не появилось");
        assertTrue(loginPage.verifyElementText(loginPage.noty_message, NotyTexts.NotyText_LOGIN_INVALID), "Неверный текст у сообщения о неудачном входе");

        loginPage.clearEmailAndPasswordFields();

        //register sensitivity
        loginPage.enterEmail(GlobalConfig.USERNAME);
        loginPage.enterPassword("aW20@!1");

        loginPage.waitForElement(loginPage.activeSubmitButton, "Submit");

        assertTrue(loginPage.clickElement(loginPage.activeSubmitButton, "Submit"), "Пытаемся войти на портал");
        loginPage.waitForElement(loginPage.noty_message, "Invalid message");
        assertTrue(verifyElementPresent(loginPage.noty_message), "Сообщение о неудачном входе не появилось");
        assertTrue(loginPage.verifyElementText(loginPage.noty_message, NotyTexts.NotyText_LOGIN_INVALID), "Неверный текст у сообщения о неудачном входе");

        loginPage.clearEmailAndPasswordFields();

        loginPage.enterEmail("user1@zveno.io@zveno.io@zveno.io");
        loginPage.enterPassword(GlobalConfig.PASSWORD);

        loginPage.waitForElement(loginPage.activeSubmitButton, "Submit");

        assertTrue(loginPage.clickElement(loginPage.activeSubmitButton, "Submit"), "Пытаемся войти на портал");
        loginPage.waitForElement(loginPage.noty_message, "Invalid message");
        assertTrue(verifyElementPresent(loginPage.noty_message), "Сообщение о неудачном входе не появилось");
        assertTrue(loginPage.verifyElementText(loginPage.noty_message, NotyTexts.NotyText_LOGIN_INVALID), "Неверный текст у сообщения о неудачном входе");

        loginPage.clearEmailAndPasswordFields();
    }
}
