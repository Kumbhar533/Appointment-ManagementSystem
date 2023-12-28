package com.appointment.management.serviceIntf;

import java.util.List;

import org.springframework.data.domain.Page;

import com.appointment.management.dto.RoleDto;
import com.appointment.management.iListDto.IListRoleDto;
import com.appointment.management.iListDto.IListRolePermissionDto;

public interface RoleServiceInterface {

	public RoleDto role(RoleDto role);

	public RoleDto updateRole(RoleDto role, Long loggedId, Long id);

	Page<IListRoleDto> getAllRoles(String pageNo, String pageSize);

	public void deleteRole(Long id, Long loggedId);

	List<IListRolePermissionDto> getPermissionsByRole(Long id);

}
