package com.appointment.management.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8013719103867006988L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "created_at")
	@CreationTimestamp
	private Date createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private Date updatedAt;

	@JoinColumn(name = "created_by")
	private Long createdBy;

	@JoinColumn(name = "updated_by")
	private Long updatedBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private GenderEnum gender;

	@Column(name = "address")
	private String address;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "developer")
	List<AppointmentEntity> Appointments;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blockingId")
	List<BlockedUserEntity> blockedUserEntities;

	@OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	List<UserRoleEntity> userRoleEntity;

	@OneToMany(mappedBy = "blockingId", fetch = FetchType.LAZY)
	List<BlockedUserEntity> blockedUser;

	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<AppointmentEntity> getAppointments() {
		return Appointments;
	}

	public void setAppointments(List<AppointmentEntity> appointments) {
		Appointments = appointments;
	}

	public List<BlockedUserEntity> getBlockedUserEntities() {
		return blockedUserEntities;
	}

	public void setBlockedUserEntities(List<BlockedUserEntity> blockedUserEntities) {
		this.blockedUserEntities = blockedUserEntities;
	}

	public List<UserRoleEntity> getUserRoleEntity() {
		return userRoleEntity;
	}

	public void setUserRoleEntity(List<UserRoleEntity> userRoleEntity) {
		this.userRoleEntity = userRoleEntity;
	}

	public List<BlockedUserEntity> getBlockedUser() {
		return blockedUser;
	}

	public void setBlockedUser(List<BlockedUserEntity> blockedUser) {
		this.blockedUser = blockedUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserEntity(Long id, String userName, String email, String password, Boolean isActive, Date createdAt,
			Date updatedAt, Long createdBy, Long updatedBy, GenderEnum gender, String address,
			List<AppointmentEntity> appointments, List<BlockedUserEntity> blockedUserEntities,
			List<UserRoleEntity> userRoleEntity, List<BlockedUserEntity> blockedUser) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.isActive = isActive;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.gender = gender;
		this.address = address;
		Appointments = appointments;
		this.blockedUserEntities = blockedUserEntities;
		this.userRoleEntity = userRoleEntity;
		this.blockedUser = blockedUser;
	}

	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
	}

}
