package io.dev.zveno;

import io.dev.zveno.constant.ValidLogInMessage;
import io.dev.zveno.constant.ValidSubmitButton;
import io.dev.zveno.listeners.AllureListeners;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({AllureListeners.class})
public class WebLogin extends ParentTest {

    //validate url and wait some content
    //wait elements is present
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование входа в приложение")
    @Description(value = "В этом тесте происходит переход на страницу входа, проверка полей электронной почты и пароля, " +
            "проверка сслыки на регистрацию, проверка ссылки на восстановление пароля и кнопки Войти")
    @Story("Тест проверяет наличие элементов входа на странице")
    @Test(priority = 1)
    public void navigateTo(){

        goingLoginPage();

        loginPage.verifyLogInFields();

        loginPage.verifyRegisterLink();

        loginPage.verifyForgotPasswordLink();

        loginPage.verifySubmitButton();

    }

    //positive
    //wait unavailability of error message
    @Test(priority = 2)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование входа в приложение")
    @Description("В этом тесте проверяется поведение полей и кнопки войти при подстановке корректных значений полей")
    @Story("Тест проверяет поведение при корректном вводе данных")
    public void insertAccountFieldsCorrectly() {

        loginPage.insertValues("correct@zveno.io", "correctPassword");

        loginPage.verifyValidFieldsMessage(ValidLogInMessage.FULL_VALID);

        loginPage.verifySubmitValidation(ValidSubmitButton.VALID);

        loginPage.clearEmailAndPasswordFields();

    }

    //negative
    //wait error message
    @Test(priority = 3)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование входа в приложение")
    @Story("Тест проверяет поведение при некорректном вводе данных")
    @Description(value = "В этом тесте проверяется наличие сообщений об ошибке, если подставлены некорректные значения полей")
    public void insertAccountFieldsIncorrectly(){

        loginPage.insertEmptyEmailAndPasswordValues();
        loginPage.verifyValidFieldsMessage(ValidLogInMessage.FULL_INVALID);
        loginPage.verifySubmitValidation(ValidSubmitButton.INVALID);

        loginPage.insertEmptyEmailValue();
        loginPage.verifyValidFieldsMessage(ValidLogInMessage.INVALID_EMAIL);
        loginPage.verifySubmitValidation(ValidSubmitButton.INVALID);
        loginPage.clearEmailAndPasswordFields();

        loginPage.insertEmptyPasswordValue();
        loginPage.verifyValidFieldsMessage(ValidLogInMessage.INVALID_PASSWORD);
        loginPage.verifySubmitValidation(ValidSubmitButton.INVALID);
        loginPage.clearEmailAndPasswordFields();

        loginPage.insertValues("incorrectEmail", "c0rrectP@ssWord");
        loginPage.verifyValidFieldsMessage(ValidLogInMessage.INVALID_EMAIL);
        loginPage.verifySubmitValidation(ValidSubmitButton.INVALID);
        loginPage.clearEmailAndPasswordFields();


    }

    //positive
    //wait portal page
    @Test(priority = 4)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование входа в приложение")
    @Story("Тест проверяет вход в систему с активными учетными данным")
    @Description(value = "В этом тесте происходит входа в систему с действительной электронной почтой и паролем")
    public void loginWithValidCredentials(){

        loginPage.logIn(GlobalConfig.USERNAME, GlobalConfig.PASSWORD);

        page.verifyAppPage();

        page.logOut();

        loginPage.logIn(GlobalConfig.USERNAME.toUpperCase(), GlobalConfig.PASSWORD);

        page.verifyAppPage();

        backHistoryInBrowser();

        page.verifyAppPage();

        page.logOut();

        loginPage.verifyLoginPage();

        loginPage.waitForElement(loginPage.loginCard, "Login Card");


    }

    //negative
    //wait content message
    @Test(priority = 5)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование входа в приложение")
    @Story("Тест проверяет вход в систему с неактивными учетными данными")
    @Description(value = "В этом тесте происходит проверка входа в систему с недействительной электронной почтой и паролем")
    public void loginWithInvalidCredentials() {

        loginPage.waitForElement(loginPage.loginCard, "Login Card");

        loginPage.logIn("user1@zveno.io", "P@ssw0rd1");

        loginPage.verifyInvalidPopupMessage();

        loginPage.clearEmailAndPasswordFields();

        loginPage.logIn(GlobalConfig.USERNAME, GlobalConfig.PASSWORD.toLowerCase());

        loginPage.verifyInvalidPopupMessage();

        loginPage.clearEmailAndPasswordFields();

        loginPage.logIn("user1@zveno.io@zveno.io@zveno.io", GlobalConfig.PASSWORD);

        loginPage.verifyInvalidPopupMessage();

        loginPage.clearEmailAndPasswordFields();

    }
}
