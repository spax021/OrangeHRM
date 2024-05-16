package ui.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.Popups;

public class DashboardTest extends BaseTest{

	private DashboardPage dashboardPage;
	private Popups popups;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		dashboardPage = new DashboardPage();
		dashboardPage.loginUserToTheSystem();
	}

	@AfterMethod
	public void tearDown() {
		quitBrowser();
	}
	
	@Test(description = "verify user can close about popup")
	public void TestVerifyUserCanCloseAboutPopup() {
		popups = new Popups();
		dashboardPage.openUsersDashboard();
		dashboardPage.clickAbout();
		popups.closePopup();
	}
	
	
	@Test(description = "verify abouts section version")
	public void TestVerifyAboutSection() {
		popups = new Popups();
		dashboardPage.openUsersDashboard();
		dashboardPage.clickAbout();
		popups.verifyHrmVersion("OrangeHRM OS 5.6.1");
	}
	
	
	@Test(description = "Logout user from system")
	public void TestLogoutUser() {
		dashboardPage.openUsersDashboard();
		dashboardPage.clickLogoutButton();
		dashboardPage.verifyUserLogedOutSuccessfully();
	}
}
