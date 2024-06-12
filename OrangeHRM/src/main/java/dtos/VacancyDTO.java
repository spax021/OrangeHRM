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
	private HiringManagerDTO hiringManager;

	public VacancyDTO() {
		super();
	}

	public VacancyDTO(int id) {
		super();
		this.id = id;
	}

	public VacancyDTO(int id, String name, boolean status, JobTitleDTO jobTitle, HiringManagerDTO hiringManager) {
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

	public HiringManagerDTO getHiringManager() {
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

	public void setHiringManager(HiringManagerDTO hiringManager) {
		this.hiringManager = hiringManager;
	}

}
