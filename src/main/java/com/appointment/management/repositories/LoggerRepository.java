package com.appointment.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.management.entities.LoggerEntity;

public interface LoggerRepository extends JpaRepository<LoggerEntity, Long> {

}
