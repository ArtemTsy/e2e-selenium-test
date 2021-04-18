package io.dev.zveno.pages;

import io.dev.zveno.GlobalHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WebPortalLoginPage extends GlobalHelper {

    @FindBy(xpath = "//*[@id='app']/div/div[2]/div/div/div[contains(@class, 'v-snack__content')]")
    public WebElement noty_message;

    @FindBy(xpath = "//*[@id='input-16'][contains(@input-label, 'Электронная почта')]")
    public WebElement emailField;

    @FindBy(xpath = "//*[@id='input-20'][contains(@input-label, 'Пароль')]")
    public WebElement passwordField;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'_input-group _input-group__buttons-row')]/button[contains(@name,'Войти')][contains(@disabled,'disabled')]")
    public WebElement disableSubmitButton;

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

    public void clearEmailAndPasswordFields() {
        emailField.clear();
        passwordField.clear();
        GlobalHelper.getDriver().navigate().refresh();
    }

    public boolean enterEmail(String email) {
        return setTextFieldText(emailField, email);
    }

    public boolean enterPassword(String password) {
        return setTextFieldText(passwordField, password);
    }

    public void logIn(String username, String password) {

        enterEmail(username);
        enterPassword(password);
        clickElement(submitButton, "Submit");

    }
}
