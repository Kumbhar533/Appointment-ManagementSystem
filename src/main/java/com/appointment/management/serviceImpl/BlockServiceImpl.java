package com.appointment.management.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.management.dto.BlockUserDto;
import com.appointment.management.entities.BlockedUserEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.iListDto.IBlockedUserDto;
import com.appointment.management.repositories.BlockedRepository;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.serviceIntf.BlockServiceInterface;
import com.appointment.management.utils.ErrorMessageConstant;

@Service
public class BlockServiceImpl implements BlockServiceInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlockedRepository blockedRepository;

	@Override
	public BlockUserDto blockUser(BlockUserDto blockuser, Long LoggedUser) {

		userRepository.findById(blockuser.getBlockedEntityId())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));

		BlockedUserEntity checkBlockedUser = blockedRepository.findByUserId(blockuser.getBlockedEntityId(), LoggedUser);

		if (checkBlockedUser == null) {

			BlockedUserEntity blockedUserEntity = new BlockedUserEntity();

			blockedUserEntity.setBlockedEntityId(blockuser.getBlockedEntityId());
			blockedUserEntity.setBlockingId(LoggedUser);
			blockedUserEntity.setReasonForBlock(blockuser.getReason());

			blockedRepository.save(blockedUserEntity);
		} else {
			throw new RuntimeException(ErrorMessageConstant.ALREADY_BLOCKED);
		}
		return blockuser;
	}

	@Override
	public List<IBlockedUserDto> listOfBlockedUsers(Long loggedId) {

		List<IBlockedUserDto> list = blockedRepository.findAllBlockedUser(loggedId);

		return list;
	}

}
