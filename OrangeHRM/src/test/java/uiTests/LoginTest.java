package uiTests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import config.PropertiesFile;
import pages.LoginPage;

@Listeners(common.Listeners.class)
public class LoginTest extends BaseTest {

	private LoginPage loginPage;
	
	@BeforeClass(alwaysRun = true)
	public void set() {
		setHeadless(true);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		loginPage = new LoginPage();
	}
	
	@AfterMethod
	public void tearDown() {
		quitBrowser();
	}
	

	@Test(description = "Login user to system")
	public void TestLoginInUser() {
		loginPage.populateUsernameAndPassword(PropertiesFile.getUsername(), PropertiesFile.getPassword());
		loginPage.clickLoginButton();
		loginPage.verifyUserLogedInSuccessfully();
	}
	
	@Test(description = "Login user to system with wrong credentials")
	public void TestLoginInWithWrongCredentials() {
		loginPage.populateUsernameAndPassword(PropertiesFile.getInvalidUsername(), PropertiesFile.getInvalidPassword());
		loginPage.clickLoginButton();
		loginPage.verifyUserDidntLogedIn();
	}

}
