package com.appointment.management.dto;

import java.util.List;

public class RolePermissionDto {

	private Long roleId;

	private List<Long> permissionId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(List<Long> permissionId) {
		this.permissionId = permissionId;
	}

	public RolePermissionDto(Long roleId, List<Long> permissionId) {
		super();
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public RolePermissionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
