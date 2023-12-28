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
@Table(name = "logger_entity")
public class LoggerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "error_message")
	private String errorMesasage;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "row")
	private int row;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErrorMesasage() {
		return errorMesasage;
	}

	public void setErrorMesasage(String errorMesasage) {
		this.errorMesasage = errorMesasage;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public LoggerEntity(Long id, String errorMesasage, Date createdAt, int row) {
		super();
		this.id = id;
		this.errorMesasage = errorMesasage;
		this.createdAt = createdAt;
		this.row = row;
	}

	public LoggerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
