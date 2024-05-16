package dtos;

public class RecruitDTO {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private int vacancyId;
	private String email;
	private String contactNumber;
	private boolean resume;
	private String keywords;
	private String dateOfApplication;
	private String comment;
	private boolean consentToKeepData;

	public RecruitDTO() {
		super();
	}

	public RecruitDTO(String firstName, String middleName, String lastName, int vacancyId, String email,
			String contactNumber, String keywords, String dateOfApplication, String comment,
			boolean consentToKeepData) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.vacancyId = vacancyId;
		this.email = email;
		this.contactNumber = contactNumber;
		this.keywords = keywords;
		this.dateOfApplication = dateOfApplication;
		this.comment = comment;
		this.consentToKeepData = consentToKeepData;
	}
	
	public int getId() {
		return id;
	}

	public boolean isResume() {
		return resume;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setResume(boolean resume) {
		this.resume = resume;
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

	public int getVacancyId() {
		return vacancyId;
	}

	public String getEmail() {
		return email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getDateOfApplication() {
		return dateOfApplication;
	}

	public String getComment() {
		return comment;
	}

	public boolean isConsentToKeepData() {
		return consentToKeepData;
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

	public void setVacancyId(int vacancyId) {
		this.vacancyId = vacancyId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setConsentToKeepData(boolean consentToKeepData) {
		this.consentToKeepData = consentToKeepData;
	}

	@Override
	public String toString() {
		return "RecruitDTO [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", vacancyId=" + vacancyId + ", email=" + email + ", contactNumber=" + contactNumber
				+ ", resume=" + resume + ", keywords=" + keywords + ", dateOfApplication=" + dateOfApplication
				+ ", comment=" + comment + ", consentToKeepData=" + consentToKeepData + "]";
	}

	
}
