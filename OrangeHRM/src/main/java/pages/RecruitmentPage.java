package pages;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

	
	public void verifyCandidateIsFound() {
		waitForElementRemovedFromDomThree(recruitmentPageElements.getResultSpinner());
		Assert.assertEquals(driver.findElement(recruitmentPageElements.getResultCandidateName()).getText(), fullName);
	}

	
}
