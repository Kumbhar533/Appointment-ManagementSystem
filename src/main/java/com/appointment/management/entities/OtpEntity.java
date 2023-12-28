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
@Table(name = "otp_entity")
public class OtpEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "otp")
	private String otp;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "expired_at")
	private Date expiredAt;

	@Column(name = "email")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public OtpEntity(Long id, String otp, Date createdAt, Date expiredAt, String email) {
		super();
		this.id = id;
		this.otp = otp;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.email = email;
	}

	public OtpEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
