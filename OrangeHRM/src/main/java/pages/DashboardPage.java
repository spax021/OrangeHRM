package pages;

import org.testng.Assert;

import config.PropertiesFile;
import elements.DashboardPageElements;

public class DashboardPage extends BasePage {

	private DashboardPageElements dashboardPageElements;
	private LoginPage loginPage;

	public DashboardPage() {
		super();
		dashboardPageElements = new DashboardPageElements();
		loginPage = new LoginPage();
	}

	public void loginUserToTheSystem() {
		loginPage.loginUserToTheSystem();

	}

	// Users About popup
	public void openUsersDashboard() {
		waitForElementClickable(dashboardPageElements.getUserDropdown());
		driver.findElement(dashboardPageElements.getUserDropdown()).click();
	}

	public void clickAbout() {
		waitForElementClickable(dashboardPageElements.getAbout());
		driver.findElement(dashboardPageElements.getAbout()).click();
	}

	public void clickLogoutButton() {
		waitForElementClickable(dashboardPageElements.getLogoutButton());
		driver.findElement(dashboardPageElements.getLogoutButton()).click();
	}

	public void verifyUserLogedOutSuccessfully() {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains(PropertiesFile.getLoginUrl()), "Current url should contain: " + PropertiesFile.getLoginUrl());
	}

	// Left bar elements - Main menu options
	public void clickRecruitmentMenu() {
		waitForElementClickable(dashboardPageElements.getRecruitmentButton());
		driver.findElement(dashboardPageElements.getRecruitmentButton()).click();
		verifyUserIsOnRecruitmentPage();
	}

	private void verifyUserIsOnRecruitmentPage() {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains(PropertiesFile.getRecruitmentPageUrl()));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
