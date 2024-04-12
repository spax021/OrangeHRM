package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
	protected static WebDriver driver;
	private static WebDriverWait wait;
	
	public void setWebDriver(WebDriver driver) {
		this.driver = driver;
		setWait(driver);
		setPageFactory(driver);
		
	}
	public void setWait(WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void waitForElementClickable(By element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementVisible(By element) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}
	
	public void waitForElementRemovedFromDomThree(By element) {
		wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(element)));
	}

	public void setPageFactory(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

}
