package com.appointment.management.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.appointment.management.entities.PermissionEntity;
import com.appointment.management.iListDto.IpermissionListDto;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

	PermissionEntity findByActionNameIgnoreCase(String actionName);

	@Query(value = "select p.id as Id, p.action_name as ActionName,p.method as Method,p.description as Description, p.base_url as BaseUrl\r\n"
			+ "from permissions p where p.is_active=true\r\n" + "", nativeQuery = true)
	Page<IpermissionListDto> findAllPermissions(Pageable pageable, Class<IpermissionListDto> class1);

}
