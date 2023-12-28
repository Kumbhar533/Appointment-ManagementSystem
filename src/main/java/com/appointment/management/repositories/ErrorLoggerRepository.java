package com.appointment.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.management.entities.ErrorLoggerEntity;

public interface ErrorLoggerRepository extends JpaRepository<ErrorLoggerEntity, Long> {

}
