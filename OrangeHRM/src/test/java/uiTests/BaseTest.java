package uiTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;

import config.PropertiesFile;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.BasePage;

public class BaseTest {

	protected static WebDriver webDriver;
	private ChromeOptions option;
	protected BasePage basePage;
	private boolean isHeadless;

	@BeforeMethod
	public void lounchApplication(ITestContext context) {
		if(isHeadless) {
			option = new ChromeOptions();
			option.addArguments("--headless=new");
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver(option);
		}else {
			webDriver = WebDriverManager.chromedriver().create();
		}
		context.setAttribute("driver", webDriver);
		
		basePage = new BasePage();
		basePage.setWebDriver(webDriver);

		webDriver.manage().deleteAllCookies();
		webDriver.manage().deleteCookieNamed("sessionKey");
		webDriver.manage().window().maximize();
		webDriver.get(PropertiesFile.getLoginUrl());

	}
	
	protected void setHeadless(boolean value) {
		isHeadless = value;
	}

	public void quitBrowser() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	public WebDriver getDriver() {
		return this.webDriver;
	}

	public void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
