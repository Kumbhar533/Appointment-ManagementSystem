package com.appointment.management.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appointment.management.entities.OtpEntity;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

	List<OtpEntity> findByExpiredAtBefore(Date date);

	@Query(value = "select oe.* from otp_entity	oe where oe.email=:email and oe.otp=:otp", nativeQuery = true)
	OtpEntity findByEmailAndOtp(@Param("email") String email, @Param("otp") String otp);

}
