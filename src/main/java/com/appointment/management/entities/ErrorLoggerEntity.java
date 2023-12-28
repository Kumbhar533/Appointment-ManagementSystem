package com.appointment.management.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "error_logger")
public class ErrorLoggerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "message", length = 10000)
	private String message;

	@Column(name = "body", length = 10000)
	private String body;

	@Column(name = "url")
	private String url;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "method")
	@Enumerated(EnumType.STRING)
	private MethodEnum method;

	@Column(name = "host")
	private String host;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public MethodEnum getMethod() {
		return method;
	}

	public void setMethod(MethodEnum method) {
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public ErrorLoggerEntity(Long id, String message, String body, String url, Date createdAt, MethodEnum method,
			String host) {
		super();
		this.id = id;
		this.message = message;
		this.body = body;
		this.url = url;
		this.createdAt = createdAt;
		this.method = method;
		this.host = host;
	}

	public ErrorLoggerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
