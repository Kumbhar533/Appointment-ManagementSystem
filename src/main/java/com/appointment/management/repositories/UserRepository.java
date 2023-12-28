package com.appointment.management.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.appointment.management.entities.UserEntity;
import com.appointment.management.iListDto.IUserListDto;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmailIgnoreCaseAndIsActiveTrue(String email);

	Page<IUserListDto> findByIsActiveTrueOrderByIdDesc(Pageable pageable, Class<IUserListDto> class1);

	UserEntity findByEmail(String email);

	UserEntity findByIdAndIsActiveTrue(Long id);

}
