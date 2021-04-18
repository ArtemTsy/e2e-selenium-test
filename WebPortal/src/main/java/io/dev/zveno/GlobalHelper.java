package io.dev.zveno;

import io.dev.zveno.base.TestBase;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.*;


public class GlobalHelper extends TestBase {

    static WebDriver driver;

    private static final int DefaultTimeout = 100;


    public void goingLoginPage(){

        driver.get(GlobalConfig.URL_Web_Login);

    }

    public boolean verifyElementText(WebElement element, String elementName, String expectedText) {

        System.out.println("Проверяем текст элемента " + elementName  + "\nТекст элемента - '" + element.getText() + "'");
        if (expectedText.contains(element.getText())) {
            System.out.println("Шаг пройден. Текст совпадает");
            return true;
        } else {
            System.err.println("Шаг не пройден. Текст отличается");
            return false;
        }
    }

    public boolean verifyElementText(WebElement element, String expectedText)
    {
        System.out.println("Текст элемента - \"" + element.getText() + "\"");
        if (element.getText().contains(expectedText)) {
            System.out.println("Текст тот же");
            return true;
        }
        else {
            System.err.println("Текст отличается. Ожидаемый текст: " + expectedText);
            return false;
        }
    }

    public boolean setTextFieldText(WebElement textField, String text)
    {
            System.out.println("Тестируем поле для текста. Идентификатор ввода - \"" + textField.getAttribute("id") + "\"");
        if (!textField.isDisplayed()) {
            System.err.println("Шаг не пройден. Текстовое поле не отображается.");
            return false;
        }
        textField.sendKeys(text);
        pauseThread(1);
        if (textField.getAttribute("value").equals(text)) {
            System.out.println("Шаг пройден. Текст - \"" + text + "\"");
            return true;
        } else {
            System.out.println("Не могу ввести текст.");
            return false;
        }
    }

    public String createFileWithText(String FileName, String TextInFile)
    {
        try {
            File statText = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Files" + File.separator + FileName);
            Writer w = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(statText)
                    )
            );
            w.write(TextInFile);
            w.close();
            return statText.getAbsolutePath();

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean clickElement(WebElement element, String elementName)
    {
        System.out.println("Проверяем элемент \"" + elementName + "\" на присутствие перед кликом");
        if (element.isDisplayed()) {
            System.out.println("Элемент присутствует. Кликаем...");
            element.click();
            return true;
        }
        else {
            System.err.println("Шаг не пройден. Элемент отсутсвует");
            return false;
        }
    }

    public static void pauseThread(int second)
    {
        try {
            Thread.sleep(second * 1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void waitForElement(WebElement element, String elementName)
    {
        System.out.println("Ждём элемент \"" + elementName + "\" (" + DefaultTimeout + " секунд)");
        new WebDriverWait(driver, DefaultTimeout).until(ExpectedConditions.visibilityOf(element));
        System.out.println("Элемент виден");
    }

}
