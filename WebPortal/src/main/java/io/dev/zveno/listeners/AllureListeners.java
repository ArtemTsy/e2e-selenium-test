package io.dev.zveno.listeners;

import com.google.common.io.Files;
import io.dev.zveno.base.TestBase;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;


public class AllureListeners extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            saveScreenshotOnFailure(TestBase.getDriver());
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveLogs(iTestResult.getMethod().getConstructorOrMethod().getName());
    }


    @Attachment
    public byte[] saveScreenshotOnFailure(WebDriver driver) throws IOException {
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("target\\allure-results\\" + screenshot.getName()));
        return Files.toByteArray(screenshot);
    }

    @Attachment
    public static String saveLogs(String message) {
        return message;
    }
}

