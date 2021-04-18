package io.dev.zveno.pages;

import io.dev.zveno.GlobalHelper;
import io.dev.zveno.constant.ValidSaveButton;
import io.dev.zveno.constant.ValidSupportMessage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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

    @FindBy(xpath = "//div[@class='v-file-input__text v-file-input__text--chips']")
    public WebElement fileInputPresent;

    @FindBy(xpath = "//*[@id='app']//button[contains(@name,'Отменить')]")
    public WebElement cancelButton;

    @FindBy(xpath = "//button[@name='Сохранить'][not(contains(@disabled,'disabled'))]")
    public WebElement activeSaveButton;

    @FindBy(xpath = "//button[@name='Сохранить'][contains(@disabled,'disabled')]")
    public WebElement deactiveSaveButton;

    @FindBy(xpath = "//*[@id='app']//button[contains(@name,'Ок')]")
    public WebElement modalWindowOkButton;

    @FindBy(xpath = "//*[@id='app']//div[contains(@class,'_modal _modal-error')]")
    public WebElement modalWindow;

    @FindBy(xpath = "//div[@data-type='select']//div//div//div//div[@role='alert']//div//div[contains(text(),'Поле обязательно для заполнения')]")
    public WebElement typeMessageAllert;

    @FindBy(xpath = "//div[@data-type='text-input']//div[contains(text(),'Поле обязательно для заполнения')]")
    public WebElement descriptionAllert;


    private void selectTypeOfMessage(WebElement typeRequest) {

        waitForElement(messageTypeSelector, "Selector");
        Assert.assertTrue(clickElement(messageTypeSelector, "Selector type of request"), "Селектор не найден");
        waitForElement(typeRequest, "Default Type");
        Assert.assertTrue(clickElement(typeRequest,"Default Type"), "Тип заявки не установлен");
    }

    private void writeDescription(String description) {

        Assert.assertTrue(setTextFieldText(descriptionInput, description), "Не удалось ввести описание проблемы");

    }

    @Step("Выход с аккаунта")
    public void logOut()
    {
        waitForElement(userDropDown, "Profile");
        clickElement(userDropDown, "Profile");

        waitForElement(logOut, "Sign Out");
        clickElement(logOut, "Sign Out");
    }

    @Step("Проверка пристуствия на странице приложения")
    public void verifyAppPage(){

        waitForElement(newsTitleItem, "News Ttitle");
        assertTrue(verifyElementText(newsTitleItem, "Добро пожаловать на новый портал Zveno.io!"), "Не страница приложения");
    }

    @Step("Переход на страницу технической поддержки")
    public void goToSupport() {

        waitForElement(support, "Support");
        Assert.assertTrue(clickElement(support, "Support"), "Элемент не представлен на странице");

    }

    @Step("Проверка наличия контента")
    public void verifySupportContent() {

        waitForElement(supportContent, "Support Content");
        assertTrue(verifyElementPresent(supportContent), "Контент отсутствует");
    }

    @Step("Проверка наличия селектора")
    public void verifyMessageTypeSelector() {

        assertTrue(verifyElementPresent(messageTypeSelector), "Селектор отсутсвует");
    }

    @Step("Проверка наличия поле описания")
    public void verifyDescriptionInput() {

        assertTrue(verifyElementPresent(descriptionInput), "Поле описания отсутствует");
    }

    @Step("Проверка наличия файлового инпута")
    public void verifyFileInput() {

        assertTrue(verifyElementPresent(fileInputPresent), "Инпут отсутствует");
    }

    @Step("Проверка наличия кнопки Отмена")
    public void verifyCancelButton() {

        assertTrue(verifyElementPresent(cancelButton), "Кнопка отсутствует");
    }

    @Step("Проверка наличия кнопки Cохранить")
    public void verifySaveButton() {

        assertTrue(verifyElementPresent(deactiveSaveButton), "Кнопка отсутствует");
    }

    @Step("Выбор стандартного типа сообщения")
    public void selectDefaultTypeOfMessage() {

        selectTypeOfMessage(defaultMessageType);
    }

    @Step("Ввод корректного описания проблемы")
    public void writeValidDescription() {

        writeDescription("1.\n2.\n3.\n4.\n5.");
    }

    @Step("Проверка активности кнопки Сохранить при {validSave}")
    public void validSaveButton(ValidSaveButton validSave) {

        switch (validSave){
            case VALID:
                assertTrue(verifyElementPresent(activeSaveButton), "Кнопка Сохранить не активна");
                break;
            case INVALID:
                assertTrue(verifyElementPresent(deactiveSaveButton), "Кнопка Сохранить активна");
                break;
        }
    }

    @Step("Очистка полей техподдержки")
    public void cleanSupportFields() {

        clickElement(cancelButton, "Отменить");
    }

    @Step("Текстовый файл создаётся и прикрепляется к сообщению")
    public void attachTextFile() {

        fileInput.sendKeys(createFileWithText("TestRequestFile.txt", "Test Request Message"));
    }

    @Step("Ввод пустых значений")
    public void insertEmptyTypeAndDescription() {

        clickElement(messageTypeSelector, "Selector Type of Message");
        clickElement(descriptionInput, "Description Input");
        clickElement(messageTypeSelector, "Selector Type of Message");
    }

    @Step("Проверка видимости сообщений об ошибке")
    public void verifyValidSupportMessage(ValidSupportMessage validSupportMessage) {

        switch(validSupportMessage) {
            case FULL_INVALID:
                assertTrue(verifyElementPresent(typeMessageAllert), "Сообщение об ошибке не показано");
                assertTrue(verifyElementPresent(descriptionAllert), "Сообщение об ошибке не показано");
                break;
            case FULL_VALID:
                assertFalse(verifyElementPresent(typeMessageAllert), "Сообщение об ошибке показано");
                assertFalse(verifyElementPresent(descriptionAllert), "Сообщение об ошибке показано");
                break;
            case INVALID_TYPE:
                assertTrue(verifyElementPresent(typeMessageAllert), "Сообщение об ошибке не показано");
                assertFalse(verifyElementPresent(descriptionAllert), "Сообщение об ошибке показано");
                break;
            case INVALID_DESCRIPTION:
                assertFalse(verifyElementPresent(typeMessageAllert), "Сообщение об ошибке показано");
                assertTrue(verifyElementPresent(descriptionAllert), "Сообщение об ошибке не показано");
                break;
        }

    }

    @Step("Ввод пустого описания")
    public void insertEmptyDescription() {

        selectTypeOfMessage(defaultMessageType);
        clickElement(descriptionInput, "Description Input");
        clickElement(messageTypeSelector, "Selector Type of Message");
        clickElement(descriptionInput, "Description Input");
    }

    @Step("Ввод пустого типа")
    public void insertEmptyType() {

        clickElement(messageTypeSelector, "Selector Type of Message");
        clickElement(descriptionInput, "Description Input");
        writeDescription("1.\n2.\n3.\n4.\n5.");
    }

    @Step("Клик по кнопке Сохранить")
    public void clickSaveButton() {

        clickElement(activeSaveButton, "Save");
    }

    @Step("Проверка наличия модального окна")
    public void verifyModalWindow() throws InterruptedException {

        Thread.sleep(1000);
        assertTrue(verifyElementPresent(modalWindow), "Модальное окно не появилось");
    }

    @Step("Закрыть модальное окно при помощт кнопки ОК")
    public void closeModalWindow() {

        clickElement(modalWindowOkButton, "ОК");
    }

}
