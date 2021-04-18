package io.dev.zveno.pages;

import io.dev.zveno.GlobalHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class WebPortalPage extends GlobalHelper {

    @FindBy(xpath = "//*[@id='app']/div/main/div/div/div/div[2]/div/div[1]/div")
    public WebElement newsTitleItem;

    @FindBy(xpath = "//*[@id='app']/div[1]/div[2]/header/div/button")
    public WebElement userDropDown;

    @FindBy(xpath = "//div[contains(text(),'Выйти')]/../..")
    public WebElement logOut;

    @FindBy(xpath = "//*[@id='app']//span[text()[contains(.,'Техподдержка')]]")
    public WebElement support;

    @FindBy(xpath = "//*[@id='app']//div[text()[contains(.,'Обращение в техподдержку')]]")
    public WebElement supportContent;

    @FindBy(xpath = "//*[@id='app']//div[contains(@aria-haspopup,'listbox')]")
    public WebElement messageTypeSelector;

    @FindBy(xpath = "//*[@role='option']//div[text()='Общее']")
    public WebElement defaultMessageType;

    @FindBy(xpath = "//div[@data-type='text-input']//div//div//div//div//textarea")
    public WebElement descriptionInput;

    @FindBy(xpath = "//input[@type='file']")
    public WebElement fileInput;

    @FindBy(xpath = "//*[@id='app']//button[contains(@name,'Отменить')]")
    public WebElement cancelButton;

    @FindBy(xpath = "//button[@name='Сохранить'][not(contains(@disabled,'disabled'))]")
    public WebElement saveButton;

    @FindBy(xpath = "//*[@id='app']//button[contains(@name,'Ок')]")
    public WebElement modalWindowOkButton;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'_modal _modal-error')]/div[contains(@class,'_modal-close-button')]")
    public WebElement modalWindowClose;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'_modal _modal-error')]")
    public WebElement modalWindow;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'modal-text')]")
    public WebElement modalText;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'modal-caption')]")
    public WebElement modalCaption;

    @FindBy(xpath = "//div[@role='alert']")
    public WebElement alertMessage;

    @FindBy(xpath = "//div[@data-type='select']//div//div//div//div[@role='alert']//div//div[contains(text(),'Поле обязательно для заполнения')]")
    public WebElement typeMessageAllert;

    @FindBy(xpath = "//div[contains(text(),'Поле обязательно для заполнения')]")
    public WebElement descriptionAllert;


    public void LogOut()
    {
        waitForElement(userDropDown, "Profile");
        clickElement(userDropDown, "Profile");

        waitForElement(logOut, "Sign Out");
        clickElement(logOut, "Sign Out");
    }

    public void goToSupport() {

        waitForElement(support, "Support");
        Assert.assertTrue(clickElement(support, "Support"), "Элемент не представлен на странице");

    }

    public void selectTypeOfMessage(WebElement typeRequest) {

        waitForElement(messageTypeSelector, "Selector");
        Assert.assertTrue(clickElement(messageTypeSelector, "Selector type of request"), "Селектор не найден");
        waitForElement(typeRequest, "Default Type");
        Assert.assertTrue(clickElement(typeRequest,"Default Type"), "Тип заявки не установлен");
    }

    public void writeDescription(String description) {

        Assert.assertTrue(setTextFieldText(descriptionInput, description), "Не удалось ввести описание проблемы");

    }

//   @Step
//    public void verifyModalWindowElements() {
//
//        waitForElement(modalWindow, "Modal Window");
//        verifyElementPresent(modalWindow);
//        verifyElementPresent(modalCaption);
//        verifyElementPresent(modalText);
//        verifyElementPresent(modalWindowClose);
//        clickElement(modalWindowOkButton, "Кнопка ОК");
//
//    }
//
//    @Step
//    public void allertsIsPresent() {
//
//        Assert.assertTrue(verifyElementPresent(typeMessageAllert), "Сообщение об ошибке не показано");
//        Assert.assertTrue(verifyElementPresent(descriptionAllert), "Сообщение об ошибке не показано");
//
//    }

}
