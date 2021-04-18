package io.dev.zveno;

import io.dev.zveno.base.TestBase;
import io.dev.zveno.pages.WebPortalLoginPage;
import io.dev.zveno.pages.WebPortalPage;
import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

public class ParentTest extends GlobalHelper {

    protected WebPortalLoginPage loginPage;
    protected WebPortalPage page;

    @BeforeTest
    public void openBrowser() {

        TestBase testBase = new TestBase();
        testBase.initialize();

        driver = getDriver();

        getPages();

    }

    @Step("Переход на стрницу входа в приложение")
    public void goingLoginPage(){

        driver.get(GlobalConfig.URL_Web_Login);

    }

    @Step("Инициализация страниц")
    public void getPages(){
        loginPage = PageFactory.initElements(TestBase.getDriver(), WebPortalLoginPage.class);
        page = PageFactory.initElements(TestBase.getDriver(), WebPortalPage.class);
    }

    @Step(value = "Проверка наличия элемента на странице")
    public boolean verifyElementPresent(WebElement element) {
        try {
            if(element.isDisplayed()) {
                System.out.println("Элемент присутствует(Отображается). Имя: " + element.getText());
                return true;
            } else {
                System.out.println("Элемент присутствует, но не отображается. Имя: " + element.getText());
                return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Элемент не присутствует(Отображается)");
            return false;
        }
    }

    @Step("Вход в аккаунт с данными {username} и {password}")
    public void logIn(String username, String password) {

        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
        clickElement(loginPage.submitButton, "Submit");

    }
    @Step("Очистка полей аккаунта")
    public void clearEmailAndPasswordFields() {
        loginPage.emailField.clear();
        loginPage.passwordField.clear();
        GlobalHelper.getDriver().navigate().refresh();
    }


    @AfterTest()
    public void closeBrowser(){
        TestBase.driver.quit();
    }
}
