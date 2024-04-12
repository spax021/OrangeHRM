package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import config.PropertiesFile;
import pages.BasePage;

public class BaseTest {

	private WebDriver webDriver;
	private ChromeOptions option;
	protected BasePage basePage;

	private final String loginUrl = PropertiesFile.getLoginUrl();

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
		webDriver.get(loginUrl);

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
