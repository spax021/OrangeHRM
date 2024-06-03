package dtos.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NationalityDTO {

	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String name;

	public NationalityDTO() {
		super();
	}

	public NationalityDTO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
