package com.appointment.management.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appointment.management.entities.RolePermissionEntity;
import com.appointment.management.iListDto.IListRolePermissionDto;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {

	@Query(value = "select permission_id from role_permission where role_id=:id", nativeQuery = true)
	List<Long> getAllpermissionByRoleId(@Param("id") Long id);

	RolePermissionEntity findByRoleEntityIdAndPermissionEntityId(Long id, Long permissionId);

	@Query(value = "select rp.permission_id as PermissionId ,p.action_name as ActionName,\r\n"
			+ "p.method as Method ,r.role_name as RoleName from role_permission rp join permissions p on rp.permission_id=p.id\r\n"
			+ "join roles r on rp.role_id=r.id where r.id=:id", nativeQuery = true)
	List<IListRolePermissionDto> findPermissionsByRoleId(@Param("id") Long id);

	@Query(value = "select p.action_name from role_permission rp \r\n"
			+ "join permissions p on p.id=rp.permission_id\r\n"
			+ "where rp.is_active=true and p.is_active=true and rp.role_id in :roleIds", nativeQuery = true)
	ArrayList<String> getPermissionNames(@Param("roleIds") ArrayList<Long> roleIds);

	@Query(value = "select p.action_name from permissions p join role_permission rp on rp.permission_id=p.id\r\n"
			+ "		join roles r on r.id=rp.role_id  join user_role ur on ur.role_id=r.id join users u on ur.user_id=u.id  \r\n"
			+ "			where u.id=:userId and ur.is_active=true and rp.is_active=true and p.is_active=true", nativeQuery = true)
	ArrayList<String> getPermissionOfUser(@Param("userId") Long userId);

}
