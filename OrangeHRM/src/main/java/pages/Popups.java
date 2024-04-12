package pages;

import org.testng.Assert;

import elements.DashboardPageElements;
import elements.PopupsElements;

public class Popups extends BasePage{
	
	private PopupsElements popupElements;
	private DashboardPage dashboardPage;

	public Popups() {
		super();
		popupElements = new PopupsElements();
	}
	//Users About popup
	public void verifyHrmVersion(String hrmVersion) {
		waitForElementVisible(popupElements.getAboutVersion());
		Assert.assertEquals(driver.findElement(popupElements.getAboutVersion()).getText(), hrmVersion, "Version should be: " + hrmVersion);
	}
	
	public void closePopup() {
		System.out.println(driver.findElement(popupElements.getAboutTitle()).getText());
		waitForElementVisible(popupElements.getAboutCloseButton());
		driver.findElement(popupElements.getAboutCloseButton()).click();
		waitForElementRemovedFromDomThree(popupElements.getAboutTitle());
	}
	

}
