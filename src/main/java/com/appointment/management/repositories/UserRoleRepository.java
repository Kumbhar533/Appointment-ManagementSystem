package com.appointment.management.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appointment.management.entities.UserRoleEntity;
import com.appointment.management.iListDto.IListRoleDto;

import jakarta.transaction.Transactional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

	@Query(value = "select * from user_role where role_id=:roleId and user_id=:userId", nativeQuery = true)
	UserRoleEntity findByUserRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

	@Transactional
	@Modifying
	@Query(value = "DELETE from user_role where role_id=:id", nativeQuery = true)
	void deleteByRoleId(@Param("id") Long id);

	@Query(value = "SELECT * FROM user_role u WHERE u.user_id=:user_id and u.is_active=true", nativeQuery = true)
	ArrayList<UserRoleEntity> getRolesOfUser(@Param("user_id") Long userId);

	@Query(value = "select ur.user_id as userId,ur.role_id as RoleId ,r.role_name as roleName from user_role ur inner join roles r on ur.role_id=r.id  where ur.user_id=:userId and ur.is_active=true", nativeQuery = true)
	ArrayList<IListRoleDto> findRoleByUserId(@Param("userId") Long roleId);

}