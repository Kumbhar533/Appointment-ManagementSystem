package com.appointment.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.appointment.management.entities.BulkUploadEntity;
import com.appointment.management.iListDto.IBulkUploadListDto;

public interface BulkUploadRepository extends JpaRepository<BulkUploadEntity, Long> {

	@Query(value = "select b.id as Id,b.file_name as FileName,b.error_count as ErrorCount,b.success_count as SuccessCount , u.user_name as UploadedBy ,(b.error_count+b.success_count)\r\n"
			+ " AS TotalRecords from bulk_upload b join users u on b.user_id = u.id", nativeQuery = true)
	List<IBulkUploadListDto> findAllBulkUploades();

}
