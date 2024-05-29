package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecruitAttachmentDTO {

	@JsonProperty("id")
	private String id;
	@JsonProperty("candidateId")
	private String candidateId;
	@JsonProperty("attachment")
	private AttachmentDTO attachment;

	public RecruitAttachmentDTO() {
		super();
	}

	public RecruitAttachmentDTO(String id, String candidateId, AttachmentDTO attachment) {
		super();
		this.id = id;
		this.candidateId = candidateId;
		this.attachment = attachment;
	}

	public String getId() {
		return id;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public AttachmentDTO getAttachment() {
		return attachment;
	}
	
	
}
