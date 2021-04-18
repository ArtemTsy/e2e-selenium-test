package io.dev.zveno;

import io.dev.zveno.constant.ValidSaveButton;
import io.dev.zveno.constant.ValidSupportMessage;
import io.dev.zveno.listeners.AllureListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Listeners({AllureListeners.class})
public class WebSupport extends ParentTest{

    @Test(priority = 1)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование отправки сообщения в техподдержку")
    @Description(value = "В этом тесте происходит переход на страницу входа и вход в существующий аккаунт")
    @Story("Тест производит производит вход на страницу приложения")
    public void login(){

        loginPage.goingLoginPage();
        loginPage.logIn(GlobalConfig.USERNAME, GlobalConfig.PASSWORD);

    }

    //check element for support
    @Test(priority = 2)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование отправки сообщения в техподдержку")
    @Description(value = "В этом тесте происходит переход на страницу техподдержки и проверка на наличие элементов отправки сообщения")
    @Story("Тест производит проверку формы техподдержки")
    public void chooseSupport(){

        page.goToSupport();
        page.verifySupportContent();
        page.verifyMessageTypeSelector();
        page.verifyDescriptionInput();
        page.verifyFileInput();
        page.verifyCancelButton();
        page.verifySaveButton();

    }

    //positive
    //parametrize
    @Test(priority = 3)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование отправки сообщения в техподдержку")
    @Description(value = "В этом тесте производится проверка поведения при вводе корректных значений")
    @Story("Тест ввода в форму допустимых значений")
    public void insertCorrectlySupportFields(){

        //Without a file
        page.cleanSupportFields();
        page.selectDefaultTypeOfMessage();
        page.writeValidDescription();
        page.validSaveButton(ValidSaveButton.VALID);
        page.cleanSupportFields();

        //With file
        page.cleanSupportFields();
        page.selectDefaultTypeOfMessage();
        page.writeValidDescription();
        page.attachTextFile();
        page.validSaveButton(ValidSaveButton.VALID);


    }

    //negative
    //parametrize
    @Test(priority = 4)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование отправки сообщения в техподдержку")
    @Description(value = "В этом тесте производится проверка поведения при вводе некорректных значений")
    @Story("Тест ввода в форму недопустимых значений")
    public void createMessageIsFalse(){

       //validate state page
        page.cleanSupportFields();
        page.insertEmptyTypeAndDescription();
        page.verifyValidSupportMessage(ValidSupportMessage.FULL_INVALID);
        page.validSaveButton(ValidSaveButton.INVALID);

        page.cleanSupportFields();
        page.insertEmptyDescription();
        page.verifyValidSupportMessage(ValidSupportMessage.INVALID_DESCRIPTION);
        page.validSaveButton(ValidSaveButton.INVALID);

        page.cleanSupportFields();
        page.insertEmptyType();
        page.verifyValidSupportMessage(ValidSupportMessage.INVALID_TYPE);
        page.validSaveButton(ValidSaveButton.INVALID);

    }

    @Test(priority = 5)
    @Epic("Тесты веб-приложения dev.zveno.io")
    @Feature("Тестирование отправки сообщения в техподдержку")
    @Description(value = "В этом тесте производится отправка сообщения в техподдержку, используя допустимые значения")
    @Story("Тест отправки сообщения")
    public void sendMessage() throws InterruptedException {

        //Without a file
        page.cleanSupportFields();
        page.selectDefaultTypeOfMessage();
        page.writeValidDescription();
        page.verifyValidSupportMessage(ValidSupportMessage.FULL_VALID);
        page.clickSaveButton();
        page.verifyModalWindow();
        page.closeModalWindow();

        //With file
        page.cleanSupportFields();
        page.selectDefaultTypeOfMessage();
        page.writeValidDescription();
        page.attachTextFile();
        page.clickSaveButton();
        page.verifyModalWindow();
        page.closeModalWindow();

    }
}
