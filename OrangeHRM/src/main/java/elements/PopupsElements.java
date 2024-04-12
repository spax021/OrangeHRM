package elements;

import org.openqa.selenium.By;

public class PopupsElements {
	
	//Users About popup
	private By aboutTitle = By.cssSelector(".orangehrm-modal-header .oxd-text");
	private By aboutCompanyName = By.cssSelector("");
	private By aboutVersion = By.cssSelector(".oxd-grid-2 .oxd-grid-item:nth-child(4)");
	private By aboutActiveEmployes = By.cssSelector("");
	private By aboutCloseButton = By.cssSelector(".oxd-dialog-close-button");
	
	public By getAboutTitle() {
		return aboutTitle;
	}
	public By getAboutCompanyName() {
		return aboutCompanyName;
	}
	public By getAboutVersion() {
		return aboutVersion;
	}
	public By getAboutActiveEmployes() {
		return aboutActiveEmployes;
	}
	public By getAboutCloseButton() {
		return aboutCloseButton;
	}
	
	

}
