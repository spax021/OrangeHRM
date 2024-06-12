package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import uiTests.BaseTest;

public class ScreenshotUtil extends BaseTest{
    public static void takeScreenshot(String testName) {
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = ".//screenshots//" + testName + "_" + timestamp + ".png";
        try {
            FileHandler.createDir(new File("screenshots")); // Ensure directory exists
            FileHandler.copy(scrFile, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
