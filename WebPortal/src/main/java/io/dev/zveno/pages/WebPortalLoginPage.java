package io.dev.zveno.pages;

import io.dev.zveno.GlobalHelper;
import io.dev.zveno.constant.ValidLogInMessage;
import io.dev.zveno.constant.ValidSubmitButton;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WebPortalLoginPage extends GlobalHelper {

    @FindBy(xpath = "//*[@id='app']/div/div[2]/div/div/div[contains(@class, 'v-snack__content')]")
    public WebElement noty_message;

    @FindBy(xpath = "//*[@id='input-16'][contains(@input-label, 'Электронная почта')]")
    public WebElement emailField;

    @FindBy(xpath = "//*[@id='input-20'][contains(@input-label, 'Пароль')]")
    public WebElement passwordField;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'_input-group _input-group__buttons-row')]/button[contains(@name,'Войти')][not(contains(@disabled,'disabled'))]")
    public WebElement activeSubmitButton;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'_input-group _input-group__buttons-row')]/button[contains(@name,'Войти')]")
    public WebElement submitButton;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'login-card')]")
    public WebElement loginCard;

    @FindBy(xpath = "//*[contains(@input-label,'Электронная почта')]//div[contains(@class, 'v-messages__wrapper')]")
    public WebElement emailValidationMessage;

    @FindBy(xpath = "//*[contains(@input-label,'Пароль')]//div[contains(@class, 'v-messages__wrapper')]")
    public WebElement passwordValidationMessage;

    @FindBy(xpath = "//a[contains(text(),'Подайте заявку на регистрацию')]")
    public WebElement singIn;

    @FindBy(xpath = "//a[contains(text(),'Забыли пароль?')]")
    public WebElement forgotPassword;

    private boolean enterEmail(String email) {
        return setTextFieldText(emailField, email);
    }

    private boolean enterPassword(String password) {
        return setTextFieldText(passwordField, password);
    }

    @Step("Вход в аккаунт с данными {username} и {password}")
    public void logIn(String username, String password) {

        waitForElement(loginCard, "LogIn Card");
        enterEmail(username);
        enterPassword(password);
        clickElement(submitButton, "Submit");

    }
    @Step("Очистка полей аккаунта")
    public void clearEmailAndPasswordFields() {
        emailField.clear();
        passwordField.clear();
        GlobalHelper.getDriver().navigate().refresh();
    }

    @Step("Подстановка пустых значений в поля")
    public void insertEmptyEmailAndPasswordValues(){

        clickElement(emailField,"Email Field");
        clickElement(passwordField, "Password Field");
        clickElement(emailField,"Email Field");
    }

    @Step("Проверка наличия полей для входа")
    public void verifyLogInFields(){

        assertTrue(verifyElementPresent(emailField), "Поле Электронная почта не находится на странице");
        assertTrue(verifyElementPresent(passwordField), "Поле Пароль не находится на странице");
    }

    @Step("Проверка наличия ссылки на регистрацию")
    public void verifyRegisterLink(){

        assertTrue(verifyElementPresent(singIn), "Ссылка на регистрацию не находится на странице");
    }

    @Step("Проверка наличия ссылка на восстановление пароля")
    public void verifyForgotPasswordLink(){

        assertTrue(verifyElementPresent(forgotPassword), "Ссыла на востановление пароля не находится на странице");
    }

    @Step("Проверка наличия кнопки Войти")
    public void verifySubmitButton(){

        assertTrue(verifyElementPresent(submitButton), "Кнопка Войти не находится на странице");
    }

    @Step("Подстановка {username} и {password}")
    public void insertValues(String username, String password){

        enterEmail(username);
        enterPassword(password);

    }

    @Step("Проверка видимости сообщений об ошибке")
    public void verifyValidFieldsMessage(ValidLogInMessage validLogInMessage){

        switch (validLogInMessage) {
            case FULL_VALID:
                assertFalse(verifyElementPresent(emailValidationMessage), "Сообщение о некорректной валидации электронной почты присутствует");
                assertFalse(verifyElementPresent(passwordValidationMessage), "Сообщение о некорректной валидации пароля присутствует");
                break;
            case FULL_INVALID:
                assertTrue(verifyElementPresent(emailValidationMessage), "Сообщение о некорректной валидации электронной почты отсутствует");
                assertTrue(verifyElementPresent(passwordValidationMessage), "Сообщение о некорректной валидации пароля отсутствует");
                break;
            case INVALID_EMAIL:
                assertTrue(verifyElementPresent(emailValidationMessage), "Сообщение о некорректной валидации электронной почты отсутствует");
                break;
            case INVALID_PASSWORD:
                assertTrue(verifyElementPresent(passwordValidationMessage), "Сообщение о некорректной валидации пароля отсутствует");
                break;
        }
    }

    @Step("Проверка активности кнопки Войти при {validSubmit}")
    public void verifySubmitValidation(ValidSubmitButton validSubmit){

        switch (validSubmit){
            case VALID:
                assertTrue(verifyElementPresent(activeSubmitButton), "Кнопка Войти не активна");
                break;
            case INVALID:
                assertFalse(verifyElementPresent(activeSubmitButton), "Кнопка Войти активна");
                break;
        }
    }

    @Step("Подстановка пустой электронной почты")
    public void insertEmptyEmailValue(){

        enterEmail("");
        enterPassword("password");
    }

    @Step("Подстановка пустого пароля")
    public void insertEmptyPasswordValue(){

        enterPassword("");
        enterEmail("qa@zveno.io");
    }

    @Step("Проверка пристуствия на странице входа")
    public void verifyLoginPage() {

        waitForElement(loginCard, "Login Card");
        assertTrue(verifyElementPresent(loginCard), "Не страница входа");
    }

    @Step("Проверка видимости всплывающих сообщений")
    public void verifyInvalidPopupMessage(){

        waitForElement(noty_message, "Pop-up Message");
        assertTrue(verifyElementPresent(noty_message), "Отсутствует всплывающее сообщение");
    }
}
