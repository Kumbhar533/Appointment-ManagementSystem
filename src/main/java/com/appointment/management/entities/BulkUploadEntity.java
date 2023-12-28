package com.appointment.management.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bulk_upload")
public class BulkUploadEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "file_name")
	private String filename;

	@Column(name = "uploaded_at")
	@CreationTimestamp
	private Date uploadedAt;

	@Column(name = "error_count")
	private int errorCount;

	@Column(name = "success_count")
	private int successCount;

	@Column(name = "user_id")
	private Long userId;

	public BulkUploadEntity(Long id, String filename, Date uploadedAt, int errorCount, int successCount, Long userId) {
		super();
		this.id = id;
		this.filename = filename;
		this.uploadedAt = uploadedAt;
		this.errorCount = errorCount;
		this.successCount = successCount;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(Date uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BulkUploadEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
