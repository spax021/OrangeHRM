package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobTitleDTO {
	@JsonProperty("id")
	private int id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("isDeleted")
	private boolean isDeleted;

	public JobTitleDTO() {
		super();
	}

	public JobTitleDTO(int id, String title, boolean isDeleted) {
		super();
		this.id = id;
		this.title = title;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
