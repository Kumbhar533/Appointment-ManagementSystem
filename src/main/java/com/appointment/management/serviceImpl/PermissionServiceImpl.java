package com.appointment.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.appointment.management.dto.PermissionRequestDto;
import com.appointment.management.entities.PermissionEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.iListDto.IpermissionListDto;
import com.appointment.management.page.Pagination;
import com.appointment.management.repositories.PermissionRepository;
import com.appointment.management.serviceIntf.PermissionServiceInterface;
import com.appointment.management.utils.ErrorMessageConstant;

@Service
public class PermissionServiceImpl implements PermissionServiceInterface {

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public PermissionRequestDto addPermissions(PermissionRequestDto permissionRequestDto, Long id) {

		PermissionEntity permission = this.permissionRepository
				.findByActionNameIgnoreCase(permissionRequestDto.getActionName());

		if (permission != null) {
			throw new ResourceNotFoundException("permission alredy exists");
		}

		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setActionName(permissionRequestDto.getActionName());
		permissionEntity.setBaseUrl(permissionRequestDto.getBaseUrl());
		permissionEntity.setDescription(permissionRequestDto.getDescription());
		permissionEntity.setMethod(permissionRequestDto.getMethod());
		permissionEntity.setCreatedBy(id);

		permissionRepository.save(permissionEntity);

		return permissionRequestDto;
	}

	@Override
	public Page<IpermissionListDto> getAllPermissions(String pageNo, String pageSize) {

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		Page<IpermissionListDto> page = permissionRepository.findAllPermissions(pageable, IpermissionListDto.class);

		return page;
	}

	@Override
	public void deletePermission(Long id, Long loggedId) {
		PermissionEntity permissionEntity = permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.PERMISSION_NOT_FOUND));

		if (permissionEntity != null) {

			permissionEntity.setIsActive(false);
			permissionEntity.setUpdatedBy(loggedId);

		}
		permissionRepository.save(permissionEntity);

	}

	@Override
	public PermissionRequestDto updatePermissions(PermissionRequestDto permissionRequestDto, Long id, Long loggedId) {

		PermissionEntity permissionEntity = permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.PERMISSION_NOT_FOUND));

		if (permissionEntity != null) {
			permissionEntity.setActionName(permissionRequestDto.getActionName());
			permissionEntity.setBaseUrl(permissionRequestDto.getBaseUrl());
			permissionEntity.setDescription(permissionRequestDto.getDescription());
			permissionEntity.setMethod(permissionRequestDto.getMethod());

			permissionEntity.setUpdatedBy(loggedId);
		}
		permissionRepository.save(permissionEntity);

		return permissionRequestDto;
	}

}
