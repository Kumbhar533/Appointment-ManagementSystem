package com.appointment.management.serviceIntf;

import java.util.List;

import com.appointment.management.dto.BlockUserDto;
import com.appointment.management.iListDto.IBlockedUserDto;

public interface BlockServiceInterface {

	BlockUserDto blockUser(BlockUserDto blockuser, Long LoggedUser);

	List<IBlockedUserDto> listOfBlockedUsers(Long loggedId);

}
