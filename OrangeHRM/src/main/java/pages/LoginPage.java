package pages;

import org.testng.Assert;

import config.PropertiesFile;
import elements.LoginPageElements;

public class LoginPage extends BasePage {

	private LoginPageElements loginPageElements;

	public LoginPage() {
		super();
		loginPageElements = new LoginPageElements();
	}

	public void populateUsernameAndPassword(String username, String password) {
		waitForElementClickable(loginPageElements.getUsername());
		driver.findElement(loginPageElements.getUsername()).clear();
		driver.findElement(loginPageElements.getUsername()).sendKeys(username);
		driver.findElement(loginPageElements.getPassword()).clear();
		driver.findElement(loginPageElements.getPassword()).sendKeys(password);
	}

	public void clickLoginButton() {
		driver.findElement(loginPageElements.getLogin()).click();
	}

	public void verifyUserLogedInSuccessfully() {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("/dashboard/index"), "Current url should contain /dashboard/index");
	}

	public void verifyUserDidntLogedIn() {
		waitForElementVisible(loginPageElements.getErrorMessage());
		Assert.assertEquals(driver.findElement(loginPageElements.getErrorMessage()).getText(), "Invalid credentials");
	}
	
	//Prepared step for dashboard page to login user to the system 
	//so we could continue testing
	public void loginUserToTheSystem() {
		populateUsernameAndPassword(PropertiesFile.getUsername(), PropertiesFile.getPassword());
		clickLoginButton();
		verifyUserLogedInSuccessfully();
	}

}
