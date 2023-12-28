package com.appointment.management.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissions")
public class PermissionEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "action_name")
	private String actionName;

	@Column(name = "base_url")
	private String baseUrl;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "method")
	private String method;

	@Column(name = "description")
	private String description;

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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permissionEntity")
	@JsonManagedReference
	List<RolePermissionEntity> rolePermissionEntities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<RolePermissionEntity> getRolePermissionEntities() {
		return rolePermissionEntities;
	}

	public void setRolePermissionEntities(List<RolePermissionEntity> rolePermissionEntities) {
		this.rolePermissionEntities = rolePermissionEntities;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PermissionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PermissionEntity(Long id, String actionName, String baseUrl, Boolean isActive, String method,
			String description, Date createdAt, Date updatedAt, Long createdBy, Long updatedBy,
			List<RolePermissionEntity> rolePermissionEntities) {
		super();
		this.id = id;
		this.actionName = actionName;
		this.baseUrl = baseUrl;
		this.isActive = isActive;
		this.method = method;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.rolePermissionEntities = rolePermissionEntities;
	}

}
