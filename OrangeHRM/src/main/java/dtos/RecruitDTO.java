package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecruitDTO {

	@JsonProperty("id")
    private int id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("middleName")
    private String middleName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("contactNumber")
    private String contactNumber;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("keywords")
    private String keywords;
    @JsonProperty("modeOfApplication")
    private int modeOfApplication;
    @JsonProperty("dateOfApplication")
    private String dateOfApplication;
    @JsonProperty("vacancy")
    private VacancyDTO vacancy;
    @JsonProperty("status")
    private StatusDTO status;
    @JsonProperty("hasAttachment")
    private boolean hasAttachment;
    @JsonProperty("consentToKeepData")
    private boolean consentToKeepData;

	public RecruitDTO() {
		super();
	}

	public RecruitDTO(String firstName, String middleName, String lastName, String email, String contactNumber,
			String comment, String keywords, int modeOfApplication, String dateOfApplication, VacancyDTO vacancy,
			StatusDTO status, boolean hasAttachment, boolean consentToKeepData) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.comment = comment;
		this.keywords = keywords;
		this.modeOfApplication = modeOfApplication;
		this.dateOfApplication = dateOfApplication;
		this.vacancy = vacancy;
		this.status = status;
		this.hasAttachment = hasAttachment;
		this.consentToKeepData = consentToKeepData;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getComment() {
		return comment;
	}

	public String getKeywords() {
		return keywords;
	}

	public int getModeOfApplication() {
		return modeOfApplication;
	}

	public String getDateOfApplication() {
		return dateOfApplication;
	}

	public VacancyDTO getVacancy() {
		return vacancy;
	}

	public StatusDTO getStatus() {
		return status;
	}

	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public boolean isConsentToKeepData() {
		return consentToKeepData;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setModeOfApplication(int modeOfApplication) {
		this.modeOfApplication = modeOfApplication;
	}

	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public void setVacancy(VacancyDTO vacancy) {
		this.vacancy = vacancy;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public void setConsentToKeepData(boolean consentToKeepData) {
		this.consentToKeepData = consentToKeepData;
	}

}
