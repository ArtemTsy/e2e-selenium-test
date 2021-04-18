package io.dev.zveno;

import io.dev.zveno.listeners.AllureListeners;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Listeners({AllureListeners.class})
public class WebSupport extends ParentTest{

    /*@BeforeClass
    public void openBrowser() {

        WebDriverManager.chromedriver().version("89.0.4389.23").setup();

        driver = getDriver();

        loginPage = PageFactory.initElements(GlobalHelper.driver, WebPortalLoginPage.class);
        page = PageFactory.initElements(GlobalHelper.driver, WebPortalPage.class);

    }*/

    @Test(priority = 1)
    public void login(){

        loginPage.goingLoginPage();
        loginPage.logIn(GlobalConfig.USERNAME, GlobalConfig.PASSWORD);

    }

    //check element for support
    @Test(priority = 2)
    public void chooseSupport(){
        //field assert......++
        page.goToSupport();

        verifyElementPresent(page.supportContent);
        verifyElementPresent(page.messageTypeSelector);
        verifyElementPresent(page.descriptionInput);
        verifyElementPresent(page.fileInput);
        verifyElementPresent(page.cancelButton);
        verifyElementPresent(page.saveButton);

    }

    //positive
    //parametrize
    @Test(priority = 3)
    private void createRequestIsTrue(){

        //Without a file
        page.selectTypeOfMessage(page.defaultMessageType);
        page.writeDescription("1.\n2.\n3.\n4.\n5.");
        assertTrue(verifyElementPresent(page.saveButton), "Кнопка не активна");
        page.clickElement(page.cancelButton, "Отменить");

        //With file
        page.selectTypeOfMessage(page.defaultMessageType);
        page.writeDescription("1.\n2.\n3.\n4.\n5.");
        page.fileInput.sendKeys(page.createFileWithText("TestRequestFile", "Test Request Message"));
        assertTrue(verifyElementPresent(page.saveButton), "Кнопка не активна");

    }

    //negative
    //parametrize
    @Test(priority = 4)
    private void createMessageIsFalse(){

        page.clickElement(page.cancelButton, "Отменить");

       //validate state page
        page.clickElement(page.messageTypeSelector, "Selector Type of Message");
        page.clickElement(page.descriptionInput, "Description Input");
        assertFalse(verifyElementPresent(page.saveButton), "Кнопка активна");
        //page.allertsIsPresent();

        page.selectTypeOfMessage(page.defaultMessageType);
        assertFalse(verifyElementPresent(page.saveButton), "Кнопка активна");
        assertTrue(verifyElementPresent(page.descriptionAllert), "Сообщение об ошибке не показано");
        page.clickElement(page.cancelButton, "Отменить");

        page.clickElement(page.messageTypeSelector, "Selector Type of Message");
        page.writeDescription("1.\n2.\n3.\n4.\n5.");
        page.clickElement(page.descriptionInput, "Description Input");
        assertFalse(verifyElementPresent(page.saveButton), "Кнопка активна");
        assertTrue(verifyElementPresent(page.typeMessageAllert), "Сообщение об ошибке не показано");
        page.clickElement(page.cancelButton, "Отменить");

    }

    @Test(priority = 5)

    private void clickToSubmit(){

        //Without a file
        page.selectTypeOfMessage(page.defaultMessageType);
        page.writeDescription("1.\n2.\n3.\n4.\n5.");
        assertTrue(verifyElementPresent(page.saveButton), "Кнопка не активна");
        page.clickElement(page.saveButton, "Save");
        waitForElement(page.modalWindow, "Модальное окно");
        assertTrue(verifyElementPresent(page.modalWindow), "Модальное окно не появилось");
        page.clickElement(page.modalWindowOkButton, "ОК");

        //With file
        page.selectTypeOfMessage(page.defaultMessageType);
        page.writeDescription("1.\n2.\n3.\n4.\n5.");
        page.fileInput.sendKeys(page.createFileWithText("TestRequestFile.txt", "Test Request Message"));
        assertTrue(page.clickElement(page.saveButton, "Save"));
        assertTrue(verifyElementPresent(page.modalWindow), "Модальное окно не появилось");

    }
}
