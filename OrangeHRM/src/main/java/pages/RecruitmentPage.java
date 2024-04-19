package pages;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import config.PropertiesFile;
import elements.RecruitmentPageElements;

public class RecruitmentPage extends BasePage{

	private RecruitmentPageElements recruitmentPageElements;

	public RecruitmentPage() {
		super();
		recruitmentPageElements = new RecruitmentPageElements();
	}
	
	public void clickAddNewCandidateButton() {
		waitForElementClickable(recruitmentPageElements.getAddNewCandidateButton());
		driver.findElement(recruitmentPageElements.getAddNewCandidateButton()).click();
		verifyUserIsOnAddNewCandidatePage();
	}
	
	private void verifyUserIsOnAddNewCandidatePage () {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains(PropertiesFile.getRecruitmentAddNewCandidatePageUrl()));
	}
	
	private static String getAbsolutePath(String relativePath) {
		try {
			File file = new File(relativePath);
			return file.getCanonicalPath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void populateNewCandidateFields() {
		waitForElementClickable(recruitmentPageElements.getFirstName());
		driver.findElement(recruitmentPageElements.getFirstName()).clear();
		driver.findElement(recruitmentPageElements.getFirstName()).sendKeys(PropertiesFile.getFirstname());
		driver.findElement(recruitmentPageElements.getMiddleName()).sendKeys(PropertiesFile.getMiddlename());
		driver.findElement(recruitmentPageElements.getLastName()).sendKeys(PropertiesFile.getLastname());
		driver.findElement(recruitmentPageElements.getVacancy()).click();
		driver.findElement(recruitmentPageElements.getVacancyOptions(PropertiesFile.getVacancy())).click();
		driver.findElement(recruitmentPageElements.getEmail()).sendKeys(PropertiesFile.getEmail());
		driver.findElement(recruitmentPageElements.getContactNumber()).sendKeys(PropertiesFile.getContactNumber());
		driver.findElement(recruitmentPageElements.getResume()).sendKeys(getAbsolutePath(PropertiesFile.getResume()));
		driver.findElement(recruitmentPageElements.getKeyWords()).sendKeys(PropertiesFile.getKeywords());;
		driver.findElement(recruitmentPageElements.getDateOfApplication()).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), PropertiesFile.getDate());;
		driver.findElement(recruitmentPageElements.getNotes()).sendKeys(PropertiesFile.getNotes());;
		driver.findElement(recruitmentPageElements.getConsentCheckbox()).click();;
	}
	
	public void saveNewCandidate() {
		driver.findElement(recruitmentPageElements.getSaveButton()).click();
	}
	
	public void cancelNewCandidate() {
		driver.findElement(recruitmentPageElements.getSaveButton()).click();
	}
	
	private String fullName = PropertiesFile.getFirstname() + " " + PropertiesFile.getMiddlename() + " " + PropertiesFile.getLastname(); 
	
	public void verifyNewCandidateIsCreated() {
		waitForElementVisible(recruitmentPageElements.getRejectButton());
		Assert.assertEquals(driver.findElement(recruitmentPageElements.getCandidateFullName()).getText(), fullName);
	}
	
	public void enterCandidateFirstname() {
		waitForElementVisible(recruitmentPageElements.getSearchCandidateName());
		driver.findElement(recruitmentPageElements.getSearchCandidateName()).click();
		driver.findElement(recruitmentPageElements.getSearchCandidateName()).sendKeys(PropertiesFile.getFirstname());
	}

	public void choseCandidateFromList() {
		waitForElementVisible(recruitmentPageElements.getSearchCandidateOptions(fullName));
		driver.findElement(recruitmentPageElements.getSearchCandidateOptions(fullName)).click();
		driver.findElement(recruitmentPageElements.getSearchButton()).click();
	}

	public void searchForCandidate() {
		driver.findElement(recruitmentPageElements.getSearchButton()).click();
	}

	/**This method is verifying that loading spiner is removed from DOM three once the action is executed and changes are saved
	*/
	public void verifySpinerIsRemovedFromDOMThree() {
		waitForElementRemovedFromDomThree(recruitmentPageElements.getResultSpinner());
	}
	
	public void verifyCandidateIsFound() {
		verifySpinerIsRemovedFromDOMThree();
		Assert.assertEquals(driver.findElement(recruitmentPageElements.getResultCandidateName()).getText(), fullName);
	}

	public void clickOnActionView() {
		driver.findElement(recruitmentPageElements.getResultResultActionView()).click();
		verifyNewCandidateIsCreated(); // Using this method since same Reject button is visible after locating existing user
	}
	
	public void clickOnEditButton() {
		waitForElementClickable(recruitmentPageElements.getCandidateEditButton());
		driver.findElement(recruitmentPageElements.getCandidateEditButton()).click();
	}
	
	private String editedFirstname = "Edit" + PropertiesFile.getFirstname();
	private String editedLastname = "Edit" + PropertiesFile.getLastname();
	private String editedFullName = editedFirstname + " " + PropertiesFile.getMiddlename() + " " + editedLastname; 
	
	/** custom JavaScript on the page is interfering with the input field therefore .clear() method is not working
	 * custom method had to be created which is simulating CONTROL + a, then DELETE
	*/
	private void removeTextFromInputField(By element) {
		driver.findElement(element).click();
		driver.findElement(element).sendKeys(Keys.CONTROL + "a");
		driver.findElement(element).sendKeys(Keys.DELETE);
	}
	
	public void editFirstname() {
		waitForElementClickable(recruitmentPageElements.getFirstName());
		removeTextFromInputField(recruitmentPageElements.getFirstName());
		driver.findElement(recruitmentPageElements.getFirstName()).sendKeys(editedFirstname);
	}
	
	public void editLastname() {
		waitForElementClickable(recruitmentPageElements.getLastName());
		removeTextFromInputField(recruitmentPageElements.getLastName());
		driver.findElement(recruitmentPageElements.getLastName()).sendKeys(editedLastname);
	}

	public void saveExistingCandidate () {
		saveNewCandidate(); //reusing this method, save button is the same
	}

	private void verifySuccessfullToastMessage() {
		waitForElementVisible(recruitmentPageElements.getToastMessageSuccessfull());
	}
	public void verifyChangesAreApplied() {
		verifySuccessfullToastMessage();
	}
}
