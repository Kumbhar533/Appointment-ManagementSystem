package com.appointment.management.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appointment.management.entities.AppointmentEntity;
import com.appointment.management.iListDto.IListAppointmentDto;
import com.appointment.management.iListDto.IListUserAppointmentsDto;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

	@Query(value = "select ar.appointment_id as AppointmentId,u.user_name as Developer \r\n"
			+ ",a.appointment_date as AppointmentDate ,ar.response as Response\r\n"
			+ "from appointment_response ar join appointment_entity a on ar.appointment_id=a.id\r\n"
			+ "join users u on u.id=a.developer_id where a.manager_id=:id", nativeQuery = true)
	Page<IListAppointmentDto> findAllAppointmentsByManagerId(Pageable pageable, @Param("id") Long id);

	@Query(value = "SELECT ar.appointment_id AS AppointmentId, u.user_name AS Manager, \r\n"
			+ "			a.appointment_date AS AppointmentDate, ar.response AS Response FROM appointment_response ar \r\n"
			+ "			JOIN appointment_entity a ON ar.appointment_id = a.id JOIN users u ON u.id = a.developer_id \r\n"
			+ "			WHERE a.developer_id =:id \r\n"
			+ "			AND (:startdate IS NULL OR a.appointment_date >= CAST(:startdate AS timestamp)) \r\n"
			+ "			AND (:enddate IS NULL OR a.appointment_date <= CAST(:enddate AS timestamp))", nativeQuery = true)
	Page<IListUserAppointmentsDto> findUserAppointments(Pageable pageable, @Param("id") Long id,
			@Param("startdate") String startdate, @Param("enddate") String enddate);

	AppointmentEntity findByIdAndIsActiveTrue(Long id);

}
