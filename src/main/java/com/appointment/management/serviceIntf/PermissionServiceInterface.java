package com.appointment.management.serviceIntf;

import org.springframework.data.domain.Page;

import com.appointment.management.dto.PermissionRequestDto;
import com.appointment.management.iListDto.IpermissionListDto;

public interface PermissionServiceInterface {

	PermissionRequestDto addPermissions(PermissionRequestDto permissionRequestDto, Long id);

	Page<IpermissionListDto> getAllPermissions(String pageNo, String pageSize);

	public void deletePermission(Long id, Long loggedId);

	PermissionRequestDto updatePermissions(PermissionRequestDto permissionRequestDto, Long id, Long loggedId);

}
