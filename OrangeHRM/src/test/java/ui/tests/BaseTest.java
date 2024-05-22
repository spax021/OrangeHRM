package ui.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;

import config.CandidateFile;
import config.PropertiesFile;
import pages.BasePage;

public class BaseTest {

	private WebDriver webDriver;
	private ChromeOptions option;
	protected BasePage basePage;

	@BeforeMethod
	public void lounchApplication() {
		setChromeDriverProperty();

//		option = new ChromeOptions();
//		option.setExperimentalOption("prefs", Map.of(
//			    "profile.default_content_settings.popups", 0,
//			    "credentials_enable_service", false,
//			    "profile.password_manager_enabled", false
//			));
//		
		webDriver = new ChromeDriver();

		basePage = new BasePage();
		basePage.setWebDriver(webDriver);

		webDriver.manage().deleteAllCookies();
		webDriver.manage().deleteCookieNamed("sessionKey");
		webDriver.manage().window().maximize();
		webDriver.get(PropertiesFile.getLoginUrl());

	}

	public void quitBrowser() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}

	private void setChromeDriverProperty() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.verboseLogging", "true");
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
