package com.appointment.management.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointment_entity")
public class AppointmentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1146124820677747197L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "appointment_date")
	private LocalDateTime appointmentDate;

	@JoinColumn(name = "developer_id")
	@ManyToOne(cascade = CascadeType.ALL)
	private UserEntity developer;

	@JoinColumn(name = "manager_id")
	@ManyToOne(cascade = CascadeType.ALL)
	private UserEntity manager;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private AppointmentResponseEnum status;

	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	@OneToMany(mappedBy = "appointmentEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AppointmentResponseEntity> responses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public UserEntity getDeveloper() {
		return developer;
	}

	public void setDeveloper(UserEntity developer) {
		this.developer = developer;
	}

	public UserEntity getManager() {
		return manager;
	}

	public void setManager(UserEntity manager) {
		this.manager = manager;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public AppointmentResponseEnum getStatus() {
		return status;
	}

	public void setStatus(AppointmentResponseEnum status) {
		this.status = status;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<AppointmentResponseEntity> getResponses() {
		return responses;
	}

	public void setResponses(List<AppointmentResponseEntity> responses) {
		this.responses = responses;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AppointmentEntity(Long id, LocalDateTime appointmentDate, UserEntity developer, UserEntity manager,
			Boolean isActive, AppointmentResponseEnum status, Date createdAt, Date updatedAt, Long createdBy,
			Long updatedBy, List<AppointmentResponseEntity> responses) {
		super();
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.developer = developer;
		this.manager = manager;
		this.isActive = isActive;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.responses = responses;
	}

	public AppointmentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
