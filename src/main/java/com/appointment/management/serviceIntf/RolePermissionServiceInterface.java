package com.appointment.management.serviceIntf;

import java.util.ArrayList;

import com.appointment.management.dto.RolePermissionDto;

public interface RolePermissionServiceInterface {

	RolePermissionDto addRolePermission(RolePermissionDto rolePermission);

	ArrayList<String> getPermissionByUserId(Long id);

}
