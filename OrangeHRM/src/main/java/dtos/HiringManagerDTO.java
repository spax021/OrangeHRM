package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HiringManagerDTO {

	@JsonProperty("id")
	private int id;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("middleName")
	private String middleName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("terminationId")
	private int terminationId;

	public HiringManagerDTO() {
		super();
	}

	public HiringManagerDTO(int id, String firstName, String middleName, String lastName, int terminationId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.terminationId = terminationId;
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

	public int getTerminationId() {
		return terminationId;
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

	public void setTerminationId(int terminationId) {
		this.terminationId = terminationId;
	}

}
