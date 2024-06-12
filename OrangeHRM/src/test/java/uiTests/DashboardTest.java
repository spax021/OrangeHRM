package uiTests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.Popups;

@Listeners(common.Listeners.class)
public class DashboardTest extends BaseTest{

	private DashboardPage dashboardPage;
	private Popups popups;

	@BeforeClass(alwaysRun = true)
	public void set() {
		setHeadless(true);
	}

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
