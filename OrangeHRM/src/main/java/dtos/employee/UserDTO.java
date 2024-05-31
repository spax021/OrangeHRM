package dtos.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

	@JsonProperty("id")
	private int id;
	@JsonProperty("userName")
	private String userName;
	@JsonProperty("deleted")
	private boolean deleted;
	@JsonProperty("status")
	private boolean status;
	@JsonProperty("employee")
	private EmployeeDTO employee;
	@JsonProperty("userRole")
	private UserRoleDTO userRole;

	public UserDTO() {
		super();
	}

	public UserDTO(int id, String userName, boolean deleted, boolean status, EmployeeDTO employee,
			UserRoleDTO userRole) {
		super();
		this.id = id;
		this.userName = userName;
		this.deleted = deleted;
		this.status = status;
		this.employee = employee;
		this.userRole = userRole;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isStatus() {
		return status;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public UserRoleDTO getUserRole() {
		return userRole;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public void setUserRole(UserRoleDTO userRole) {
		this.userRole = userRole;
	}

}
