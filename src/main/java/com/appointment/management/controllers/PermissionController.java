package com.appointment.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.dto.ListResponseDto;
import com.appointment.management.dto.PaginationResponse;
import com.appointment.management.dto.PermissionRequestDto;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.iListDto.IpermissionListDto;
import com.appointment.management.serviceIntf.PermissionServiceInterface;
import com.appointment.management.utils.Constant;
import com.appointment.management.utils.ErrorKeyConstant;
import com.appointment.management.utils.ErrorMessageConstant;
import com.appointment.management.utils.GlobalFunctions;
import com.appointment.management.utils.SuccessKeyConstant;
import com.appointment.management.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/permission")
@SecurityRequirement(name = "jwt")
public class PermissionController {

	@Autowired
	private PermissionServiceInterface permissionServiceInterface;

	@PreAuthorize("hasRole('AddPermission')")
	@PostMapping
	public ResponseEntity<?> addPermission(@RequestBody PermissionRequestDto dto,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {

			this.permissionServiceInterface.addPermissions(dto, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.PERMISSION_ADDED,
					SuccessKeyConstant.PERMISSION_M032301), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.PERMISSION_NOT_ADDED,
					ErrorKeyConstant.PERMISSION_E032303), HttpStatus.BAD_REQUEST);

		}

	}

	@PreAuthorize("hasRole('PermissionList')")
	@GetMapping("/all")
	public ResponseEntity<?> getAllPermissions(
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize) {

		try {
			Page<IpermissionListDto> permissions = this.permissionServiceInterface.getAllPermissions(pageNo, pageSize);

			PaginationResponse paginationResponse = new PaginationResponse();
			paginationResponse.setPageSize(permissions.getSize());
			paginationResponse.setTotal(permissions.getTotalElements());
			paginationResponse.setPageNumber(permissions.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(permissions.getContent(), paginationResponse),
					HttpStatus.OK);

		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.PERMISSION_NOT_FOUND,
					ErrorKeyConstant.PERMISSION_E032301), HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("hasRole('DeletePermission')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePermission(@PathVariable(value = "id") Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {
		try {

			this.permissionServiceInterface.deletePermission(id, userId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.PERMISSION_DELETED,
					SuccessKeyConstant.PERMISSION_M032303), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.PERMISSION_NOT_FOUND,
					ErrorKeyConstant.PERMISSION_E032301), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UpdatePermission')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePermission(@PathVariable(value = "id") Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long loggedId,
			@RequestBody PermissionRequestDto permissionRequestDto) {
		try {

			this.permissionServiceInterface.updatePermissions(permissionRequestDto, id, loggedId);

			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.PERMISSION_UPDATED,
					SuccessKeyConstant.PERMISSION_M032302, permissionRequestDto), HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.PERMISSION_NOT_FOUND,
					ErrorKeyConstant.PERMISSION_E032301), HttpStatus.BAD_REQUEST);

		}

	}
}
