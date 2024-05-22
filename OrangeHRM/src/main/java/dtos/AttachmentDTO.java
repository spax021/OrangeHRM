package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttachmentDTO {

	@JsonProperty("fileName")
	private String fileName;
	@JsonProperty("fileType")
	private String fileType;
	@JsonProperty("fileSize")
	private String fileSize;

	public AttachmentDTO() {
		super();
	}

	public AttachmentDTO(String fileName, String fileType, String fileSize) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

}
