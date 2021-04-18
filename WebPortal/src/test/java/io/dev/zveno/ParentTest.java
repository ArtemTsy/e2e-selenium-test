package io.dev.zveno;

import io.dev.zveno.base.TestBase;
import io.dev.zveno.pages.WebPortalLoginPage;
import io.dev.zveno.pages.WebPortalPage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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

    @AfterTest
    public void closeBrowser(){
        TestBase.driver.quit();
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

    @Step("Нажатие кнопки назад в браузере")
    public void backHistoryInBrowser(){

        driver.navigate().back();
    }
}
