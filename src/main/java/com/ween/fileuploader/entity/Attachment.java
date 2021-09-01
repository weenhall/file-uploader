package com.ween.fileuploader.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
public class Attachment {

	@Id
	@GenericGenerator(name = "uuid-generator",strategy = "uuid")
	@GeneratedValue(generator = "uuid-generator")
	private String id;
	private String fileSource;
	private String fileName;
	private String fileUrl;
	private String fileType;
	private String fileBucket;
	private String uploader;
	private String lastModifier;
	private LocalDateTime uploadTime;
	private LocalDateTime lastModifierTime;
	@Transient
	private String downloadUrl;
	@Transient
	private boolean editable;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public LocalDateTime getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public LocalDateTime getLastModifierTime() {
		return lastModifierTime;
	}

	public void setLastModifierTime(LocalDateTime lastModifierTime) {
		this.lastModifierTime = lastModifierTime;
	}

	public String getFileBucket() {
		return fileBucket;
	}

	public void setFileBucket(String fileBucket) {
		this.fileBucket = fileBucket;
	}
}
