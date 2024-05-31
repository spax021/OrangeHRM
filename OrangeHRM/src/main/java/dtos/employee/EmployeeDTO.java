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

	public EmployeeDTO() {
		super();
	}

	public EmployeeDTO(int empNumber, String employeeId, String firstName, String middleName, String lastName,
			Integer terminationId) {
		super();
		this.empNumber = empNumber;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.terminationId = terminationId;
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

}
