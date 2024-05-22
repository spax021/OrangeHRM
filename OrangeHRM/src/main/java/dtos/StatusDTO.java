package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusDTO {

	@JsonProperty("id")
	private int id;
	@JsonProperty("label")
	private String label;

	public StatusDTO() {
		super();
	}

	public StatusDTO(int id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
