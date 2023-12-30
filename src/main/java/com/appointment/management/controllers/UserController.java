package com.appointment.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.dto.IUserIdDto;
import com.appointment.management.dto.ListResponseDto;
import com.appointment.management.dto.PaginationResponse;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.dto.UpdateUserDto;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.iListDto.IUserListDto;
import com.appointment.management.serviceIntf.UserServiceInterface;
import com.appointment.management.utils.Constant;
import com.appointment.management.utils.ErrorKeyConstant;
import com.appointment.management.utils.ErrorMessageConstant;
import com.appointment.management.utils.GlobalFunctions;
import com.appointment.management.utils.SuccessKeyConstant;
import com.appointment.management.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "jwt")
public class UserController {

	@Autowired
	private UserServiceInterface userServiceInterface;

	@PreAuthorize("hasRole('UserUpdate')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			this.userServiceInterface.updateUserDto(updateUserDto, id, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.USER_UPDATED,
					SuccessKeyConstant.USER_M031103, updateUserDto), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.USER_NOT_FOUND, ErrorKeyConstant.USER_E031101),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorKeyConstant.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('UserDelete')")
	@DeleteMapping("/delete/{Userid}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long Userid,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long Logged) {

		try {

			this.userServiceInterface.deleteUser(Userid, Logged);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageConstant.USER_DELETED, SuccessKeyConstant.USER_M031102),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.USER_NOT_FOUND, ErrorKeyConstant.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('UserList')")
	@GetMapping()
	public ResponseEntity<?> getAllUser(
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize) {
		try {

			Page<IUserListDto> users = this.userServiceInterface.getAll(pageNo, pageSize);

			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(users.getSize());
			paginationResponse.setTotal(users.getTotalElements());
			paginationResponse.setPageNumber(users.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(users.getContent(), paginationResponse), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorKeyConstant.USER_E031101),
					HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id) {

		try {

			IUserIdDto user = this.userServiceInterface.getUserById(id);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageConstant.USER_FETCHED, SuccessKeyConstant.USER_M031101, user),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.USER_NOT_FOUND, ErrorKeyConstant.USER_E031101),
					HttpStatus.BAD_REQUEST);
		}
	}

}
