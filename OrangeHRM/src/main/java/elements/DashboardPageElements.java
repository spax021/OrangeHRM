package elements;

import org.openqa.selenium.By;

public class DashboardPageElements {

	//user dropdown list
	private By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
	private By about = By.cssSelector("a.oxd-userdropdown-link[href$='#']");
	private By support = By.cssSelector("a.oxd-userdropdown-link[href$='/support']");
	private By changePassword = By.cssSelector("a.oxd-userdropdown-link[href$='/updatePassword']");
	private By logoutButton = By.cssSelector("a.oxd-userdropdown-link[href$='/logout']");
	
	//Left bar elements (Main menu items)
	private By recruitmentButton = By.cssSelector("a.oxd-main-menu-item[href$='/viewRecruitmentModule']");
	
	public By getUserDropdown() {
		return userDropdown;
	}

	public By getAbout() {
		return about;
	}

	public By getSupport() {
		return support;
	}

	public By getChangePassword() {
		return changePassword;
	}
	
	public By getLogoutButton() {
		return logoutButton;
	}

	public By getRecruitmentButton() {
		return recruitmentButton;
	}


}

