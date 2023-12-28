package com.appointment.management.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.management.entities.RoleEntity;
import com.appointment.management.iListDto.IListRoleDto;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findByRoleNameIgnoreCase(String roleName);

	Page<IListRoleDto> findByOrderByIdDesc(Pageable pageable, Class<IListRoleDto> class1);

}
