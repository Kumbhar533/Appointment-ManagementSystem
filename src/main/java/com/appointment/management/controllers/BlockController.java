package com.appointment.management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.management.dto.BlockUserDto;
import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.iListDto.IBlockedUserDto;
import com.appointment.management.serviceIntf.BlockServiceInterface;
import com.appointment.management.utils.ErrorKeyConstant;
import com.appointment.management.utils.GlobalFunctions;
import com.appointment.management.utils.SuccessKeyConstant;
import com.appointment.management.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/block")
@SecurityRequirement(name = "jwt")
public class BlockController {

	@Autowired
	private BlockServiceInterface blockServiceInterface;

	@PreAuthorize("hasRole('BlockUser')")
	@PostMapping
	public ResponseEntity<?> blockUser(@RequestBody BlockUserDto blockuser,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			blockServiceInterface.blockUser(blockuser, userId);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageConstant.BLOCK_USER, SuccessKeyConstant.BLOCK_USER_E031301),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorKeyConstant.BLOCK_USER_E032301),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('BlockedUserList')")
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllBlockedUser(
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {

			List<IBlockedUserDto> listOfBlockedUsers = this.blockServiceInterface.listOfBlockedUsers(userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.BLOCK_USERS,
					SuccessKeyConstant.BLOCK_USER_E031302, listOfBlockedUsers), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

}
