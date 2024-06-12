package dtos.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDTO {

	@JsonProperty("empNumber")
	private int empNumber;
	@JsonProperty("employeeId")
	private String employeeId;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("middleName")
	private String middleName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("terminationId")
	private Integer terminationId;
	@JsonProperty("otherId")
	private String otherId;
	@JsonProperty("driverLicenceNo")
	private String driverLicenceNo;
	@JsonProperty("driverLicenceExpiredDate")
	private String driverLicenceExpiredDate;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("maritalStatus")
	private String maritalStatus;
	@JsonProperty("birthday")
	private String birthday;
	@JsonProperty("nationality")
	private NationalityDTO nationality;

	public EmployeeDTO() {
		super();
	}
	

	public EmployeeDTO(int empNumber, String firstName, String middleName, String lastName) {
		super();
		this.empNumber = empNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	public EmployeeDTO(int empNumber, String employeeId, String firstName, String middleName, String lastName,
			Integer terminationId, String otherId, String driverLicenceNo, String driverLicenceExpiredDate,
			String gender, String maritalStatus, String birthday, NationalityDTO nationality) {
		super();
		this.empNumber = empNumber;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.terminationId = terminationId;
		this.otherId = otherId;
		this.driverLicenceNo = driverLicenceNo;
		this.driverLicenceExpiredDate = driverLicenceExpiredDate;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
		this.birthday = birthday;
		this.nationality = nationality;
	}

	public int getEmpNumber() {
		return empNumber;
	}

	public String getEmployeeId() {
		return employeeId;
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

	public Integer getTerminationId() {
		return terminationId;
	}

	public String getOtherId() {
		return otherId;
	}

	public String getDriverLicenceNo() {
		return driverLicenceNo;
	}

	public String getDriverLicenceExpiredDate() {
		return driverLicenceExpiredDate;
	}

	public String getGender() {
		return gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getBirthday() {
		return birthday;
	}

	public NationalityDTO getNationality() {
		return nationality;
	}

	public void setEmpNumber(int empNumber) {
		this.empNumber = empNumber;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public void setTerminationId(Integer terminationId) {
		this.terminationId = terminationId;
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}

	public void setDriverLicenceNo(String driverLicenceNo) {
		this.driverLicenceNo = driverLicenceNo;
	}

	public void setDriverLicenceExpiredDate(String driverLicenceExpiredDate) {
		this.driverLicenceExpiredDate = driverLicenceExpiredDate;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setNationality(NationalityDTO nationality) {
		this.nationality = nationality;
	}

}
