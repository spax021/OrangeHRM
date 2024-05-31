package dtos.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRoleDTO {

	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("displayName")
	private String displayName;

	public UserRoleDTO() {
		super();
	}

	public UserRoleDTO(int id, String name, String displayName) {
		super();
		this.id = id;
		this.name = name;
		this.displayName = displayName;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
