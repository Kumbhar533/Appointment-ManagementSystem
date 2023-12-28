package com.appointment.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.appointment.management.dto.RoleDto;
import com.appointment.management.entities.PermissionEntity;
import com.appointment.management.entities.RoleEntity;
import com.appointment.management.entities.RolePermissionEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.iListDto.IListRoleDto;
import com.appointment.management.iListDto.IListRolePermissionDto;
import com.appointment.management.page.Pagination;
import com.appointment.management.repositories.PermissionRepository;
import com.appointment.management.repositories.RolePermissionRepository;
import com.appointment.management.repositories.RoleRepository;
import com.appointment.management.repositories.UserRoleRepository;
import com.appointment.management.serviceIntf.RoleServiceInterface;
import com.appointment.management.utils.ErrorMessageConstant;

@Service
public class RoleServiceImpl implements RoleServiceInterface {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public RoleDto role(RoleDto role) {
		RoleEntity roleEntity = new RoleEntity();
		RoleEntity roleEntity1 = roleRepository.findByRoleNameIgnoreCase(role.getRoleName());
		if (roleEntity1 != null) {

			throw new ResourceNotFoundException(ErrorMessageConstant.ROLE_ALREADY_PRESENT);
		}

		else {

			roleEntity.setRoleName(role.getRoleName());
			roleEntity.setDescription(role.getDescription());

			roleRepository.save(roleEntity);

		}

		ArrayList<RolePermissionEntity> rolePermission = new ArrayList<RolePermissionEntity>();

		for (int i = 0; i < role.getPermissions().size(); i++) {
			PermissionEntity permissionEntity = permissionRepository.findById(role.getPermissions().get(i))
					.orElseThrow(() -> new ResourceAccessException(ErrorMessageConstant.PERMISSION_NOT_FOUND));

			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();

			rolePermissionEntity.setPermissionEntity(permissionEntity);
			rolePermissionEntity.setRoleEntity(roleEntity);

			rolePermission.add(rolePermissionEntity);
		}

		rolePermissionRepository.saveAll(rolePermission);
		return role;
	}

	@Override
	public RoleDto updateRole(RoleDto role, Long loggedId, Long id) {

		RoleEntity entity = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_FOUND));

		entity.setRoleName(role.getRoleName());
		entity.setDescription(role.getDescription());
		entity.setUpdatedBy(loggedId);
		roleRepository.save(entity);

		List<Long> permissions = rolePermissionRepository.getAllpermissionByRoleId(id);

		List<Long> newPermissions = role.getPermissions();

		List<Long> permissionsToAdd = new ArrayList<>(newPermissions);
		permissionsToAdd.removeAll(permissions);

		List<Long> permissionsToRemove = new ArrayList<>(permissions);
		permissionsToRemove.removeAll(newPermissions);

		for (Long permissionId : permissionsToAdd) {
			PermissionEntity permissionEntity = this.permissionRepository.findById(permissionId)
					.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.PERMISSION_NOT_FOUND));

			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
			rolePermissionEntity.setRoleEntity(entity);
			rolePermissionEntity.setPermissionEntity(permissionEntity);

			this.rolePermissionRepository.save(rolePermissionEntity);

		}

		for (Long permissionId : permissionsToRemove) {
			// Retrieve the RolePermissionEntity to be removed
			RolePermissionEntity rolePermissionEntity = this.rolePermissionRepository
					.findByRoleEntityIdAndPermissionEntityId(id, permissionId);

			// Delete the RolePermissionEntity
			this.rolePermissionRepository.delete(rolePermissionEntity);
		}
		return role;
	}

	@Override
	public Page<IListRoleDto> getAllRoles(String pageNo, String pageSize) {
		Page<IListRoleDto> page = null;

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		page = roleRepository.findByOrderByIdDesc(pageable, IListRoleDto.class);
		return page;
	}

	@Override
	public void deleteRole(Long id, Long loggedId) {

		RoleEntity roleEntity = roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_FOUND));

		roleEntity.setIsActive(false);
		roleEntity.setUpdatedBy(loggedId);
		roleRepository.save(roleEntity);

		userRoleRepository.deleteByRoleId(id);

	}

	@Override
	public List<IListRolePermissionDto> getPermissionsByRole(Long id) {

		roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_FOUND));

		List<IListRolePermissionDto> allPermissions = rolePermissionRepository.findPermissionsByRoleId(id);

		return allPermissions;
	}

}
