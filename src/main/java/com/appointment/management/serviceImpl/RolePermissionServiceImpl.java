package com.appointment.management.serviceImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.management.dto.RolePermissionDto;
import com.appointment.management.entities.PermissionEntity;
import com.appointment.management.entities.RoleEntity;
import com.appointment.management.entities.RolePermissionEntity;
import com.appointment.management.entities.UserRoleEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.repositories.PermissionRepository;
import com.appointment.management.repositories.RolePermissionRepository;
import com.appointment.management.repositories.RoleRepository;
import com.appointment.management.repositories.UserRoleRepository;
import com.appointment.management.serviceIntf.RolePermissionServiceInterface;
import com.appointment.management.utils.ErrorMessageConstant;

@Service
public class RolePermissionServiceImpl implements RolePermissionServiceInterface {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public RolePermissionDto addRolePermission(RolePermissionDto rolePermission) {

		RoleEntity roleEntity = roleRepository.findById(rolePermission.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_FOUND));

		ArrayList<Long> permissions = new ArrayList<>(rolePermission.getPermissionId());

		ArrayList<RolePermissionEntity> rolePermissions = new ArrayList<RolePermissionEntity>();

		for (int i = 0; i < permissions.size(); i++) {
			PermissionEntity permissionEntity = permissionRepository.findById(permissions.get(i))
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.PERMISSION_NOT_FOUND));

			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();

			rolePermissionEntity.setRoleEntity(roleEntity);
			rolePermissionEntity.setPermissionEntity(permissionEntity);
			rolePermissions.add(rolePermissionEntity);
		}
		rolePermissionRepository.saveAll(rolePermissions);

		return rolePermission;
	}

	@Override
	public ArrayList<String> getPermissionByUserId(Long id) {
		ArrayList<UserRoleEntity> roles = userRoleRepository.getRolesOfUser(id);
		ArrayList<Long> userRole = new ArrayList<Long>();
		for (int i = 0; i < roles.size(); i++) {

			userRole.add(roles.get(i).getRoles().getId());

		}
		ArrayList<String> permissions = rolePermissionRepository.getPermissionNames(userRole);
		return permissions;
	}

}
