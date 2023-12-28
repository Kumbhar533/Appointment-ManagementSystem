package com.appointment.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointment.management.entities.AppointmentResponseEntity;

@Repository
public interface AppointmentResponseReposistory extends JpaRepository<AppointmentResponseEntity, Long> {

	// AppointmentResponseEntity findByAppointmentId(Long id);

}
