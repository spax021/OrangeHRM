package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VacancyDTO {

	@JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("jobTitle")
    private JobTitleDTO jobTitle;
    @JsonProperty("hiringManager")
	private String hiringManager;

	public VacancyDTO() {
		super();
	}

	public VacancyDTO(int id) {
		super();
		this.id = id;
	}

	public VacancyDTO(int id, String name, boolean status, JobTitleDTO jobTitle, String hiringManager) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.jobTitle = jobTitle;
		this.hiringManager = hiringManager;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isStatus() {
		return status;
	}

	public JobTitleDTO getJobTitle() {
		return jobTitle;
	}

	public String getHiringManager() {
		return hiringManager;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setJobTitle(JobTitleDTO jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setHiringManager(String hiringManager) {
		this.hiringManager = hiringManager;
	}

}
