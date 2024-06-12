package common;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.ScreenshotUtil;

public class Listeners extends ScreenshotUtil implements ITestListener{
	
    @Override
    public void onTestStart(ITestResult result) {
    	webDriver = (WebDriver) result.getTestContext().getAttribute("driver");
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	webDriver = (WebDriver) result.getTestContext().getAttribute("driver");
        if (webDriver != null) {
            ScreenshotUtil.takeScreenshot(result.getName());
        }
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {}

    @Override
    public void onTestSkipped(ITestResult result) {}

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onFinish(ITestContext context) {}
}
