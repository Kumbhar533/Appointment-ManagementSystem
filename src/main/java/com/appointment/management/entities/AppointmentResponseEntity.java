package com.appointment.management.entities;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment_response")
public class AppointmentResponseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3616969118088978207L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private UserEntity userEntity;

	@JoinColumn(name = "appointment_id")
	@ManyToOne
	private AppointmentEntity appointmentEntity;

	@Enumerated(EnumType.STRING)
	@Column(name = "response")
	private AppointmentResponseEnum response;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	public AppointmentResponseEntity(Long id, UserEntity userEntity, AppointmentEntity appointmentEntity,
			AppointmentResponseEnum response, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.userEntity = userEntity;
		this.appointmentEntity = appointmentEntity;
		this.response = response;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public AppointmentEntity getAppointmentEntity() {
		return appointmentEntity;
	}

	public void setAppointmentEntity(AppointmentEntity appointmentEntity) {
		this.appointmentEntity = appointmentEntity;
	}

	public AppointmentResponseEnum getResponse() {
		return response;
	}

	public void setResponse(AppointmentResponseEnum response) {
		this.response = response;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AppointmentResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
