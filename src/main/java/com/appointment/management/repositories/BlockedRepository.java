package com.appointment.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appointment.management.entities.BlockedUserEntity;
import com.appointment.management.iListDto.IBlockedUserDto;

public interface BlockedRepository extends JpaRepository<BlockedUserEntity, Long> {

	@Query(value = "select b.* from blocked_user b where b.blocked_entity_id=:id and b.blocking_id=:managerId", nativeQuery = true)
	BlockedUserEntity findByUserId(@Param("id") Long id, @Param("managerId") Long managerId);

	@Query(value = "select b.blocking_id as BlockingId,b.blocked_entity_id as UserBlockedId ,b.reason as Reason,b.block_status as BlockStatus from blocked_user b where  b.blocking_id=:id", nativeQuery = true)
	List<IBlockedUserDto> findAllBlockedUser(@Param("id") Long id);

}
