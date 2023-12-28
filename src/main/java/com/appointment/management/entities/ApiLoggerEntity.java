package com.appointment.management.entities;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_logger")
public class ApiLoggerEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "url")
	private String url;

	@Column(name = "method")
	private String method;

	@Column(name = "body")
	private String body;

	@Column(name = "host")
	private String host;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "ip_address")
	private String ipAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ApiLoggerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiLoggerEntity(Long id, String url, String method, String body, String host, Date createdAt, String ipAddress) {
		super();
		this.id = id;
		this.url = url;
		this.method = method;
		this.body = body;
		this.host = host;
		this.createdAt = createdAt;
		this.ipAddress = ipAddress;
	}

}
