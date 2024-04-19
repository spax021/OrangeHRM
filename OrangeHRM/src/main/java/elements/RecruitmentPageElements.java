package elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RecruitmentPageElements {
	
	// Recruitment search section elements
	private By searchCandidateName = By.cssSelector(".oxd-autocomplete-text-input>input");
	private By searchCandidateOptions;
	private By searchButton = By.cssSelector("button[type='submit']");
	
	// Recruitment view list of records elements	
	private By addNewCandidateButton = By.cssSelector(".orangehrm-paper-container .orangehrm-header-container .oxd-button");
	
	// Recruitment add new candidate elements
	private By firstName = By.cssSelector("input[name='firstName']");
	private By middleName = By.cssSelector("input[name='middleName']");
	private By lastName = By.cssSelector("input[name='lastName']");
	private By vacancy = By.cssSelector(".oxd-select-text--arrow");
	private By vacancyOptions;	
	private By email = By.cssSelector(".oxd-form-row:nth-child(3) .oxd-grid-item .oxd-input--active");
	private By contactNumber = By.cssSelector(".oxd-form-row:nth-child(3) .oxd-grid-item:nth-child(2) .oxd-input--active");
	private By resume = By.cssSelector(".oxd-file-input");
	private By keyWords = By.cssSelector(".oxd-form-row:nth-child(5) .oxd-input");
	private By dateOfApplication = By.cssSelector(".oxd-date-input .oxd-input");
	private By notes = By.cssSelector(".oxd-form-row:nth-child(6) .oxd-textarea");
	private By consentCheckbox = By.cssSelector(".oxd-form-row:nth-child(7) .oxd-checkbox-input-icon");
	private By cancelButton = By.cssSelector(".oxd-form-actions .oxd-button:nth-child(2)");
	private By saveButton = By.cssSelector("button[type='submit']");
	//Candidate EDIT section
	private By candidateEditButton = By.cssSelector(".oxd-switch-wrapper .oxd-switch-input");
	
		
	// Recruitment Application Stage of new candidate elements
	private By candidateFullName = By.cssSelector(".oxd-grid-3 .oxd-text");
	private By rejectButton = By.cssSelector("button.oxd-button.oxd-button--danger");

	// Recruitment result list section
	private By resultResultsNumber;
	private By resultResultDeleteButton;
	private By resultResultCheckBox;
	private By resultCandidateName = By.cssSelector(".oxd-table-card .oxd-table-cell:nth-child(3)");
	private By resultResultActionView = By.cssSelector(".oxd-table-card .oxd-table-cell-actions .bi-eye-fill");
	private By resultResultActionDelete;
	private By resultSpinner = By.cssSelector(".oxd-loading-spinner");
	
	//Toast message / bottom left
	private By toastMessageSuccessfull = By.id("oxd-toaster_1");
	
	public By getToastMessageSuccessfull() {
		return toastMessageSuccessfull;
	}	
	public By getCandidateEditButton() {
		return candidateEditButton;
	}	
	public By getResultResultsNumber() {
		return resultResultsNumber;
	}
	public By getResultResultDeleteButton() {
		return resultResultDeleteButton;
	}
	public By getResultResultCheckBox() {
		return resultResultCheckBox;
	}
	public By getResultCandidateName() {
		return resultCandidateName;
	}
	public By getResultResultActionView() {
		return resultResultActionView;
	}
	public By getResultResultActionDelete() {
		return resultResultActionDelete;
	}
	public By getResultSpinner() {
		return resultSpinner;
	}
	public By getRejectButton() {
		return rejectButton;
	}
	public By getCandidateFullName() {
		return candidateFullName;
	}
	public By getAddNewCandidateButton() {
		return addNewCandidateButton;
	}
	public By getFirstName() {
		return firstName;
	}
	public By getMiddleName() {
		return middleName;
	}
	public By getLastName() {
		return lastName;
	}
	public By getVacancy() {
		return vacancy;
	}
	public By getSearchCandidateName() {
		return searchCandidateName;
	}
	public By getSearchCandidateOptions(String fullName) {
		// div.oxd-autocomplete-option>span
		// /span[contains(text(), '" + "testFirstname" + "')
		searchCandidateOptions = By.xpath("//span[contains(text(), '" + fullName + "')]");
		return searchCandidateOptions;
	}
	public By getSearchButton() {
		return searchButton;
	}
	
	public By getVacancyOptions(String vacancyOption) {
		vacancyOptions = By.xpath("//*[contains(text(), '" + vacancyOption + "')]");
		return vacancyOptions;
	}
	public By getEmail() {
		return email;
	}
	public By getContactNumber() {
		return contactNumber;
	}
	public By getResume() {
		return resume;
	}
	public By getKeyWords() {
		return keyWords;
	}
	public By getDateOfApplication() {
		return dateOfApplication;
	}
	public By getNotes() {
		return notes;
	}
	public By getConsentCheckbox() {
		return consentCheckbox;
	}
	public By getCancelButton() {
		return cancelButton;
	}
	public By getSaveButton() {
		return saveButton;
	}
	
	
}
