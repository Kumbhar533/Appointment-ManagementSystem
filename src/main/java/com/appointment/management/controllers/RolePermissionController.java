package com.appointment.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.dto.RolePermissionDto;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.serviceIntf.RolePermissionServiceInterface;
import com.appointment.management.utils.ErrorKeyConstant;
import com.appointment.management.utils.ErrorMessageConstant;
import com.appointment.management.utils.SuccessKeyConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/rolePermission")
@SecurityRequirement(name = "jwt")
public class RolePermissionController {

	@Autowired
	private RolePermissionServiceInterface rolePermissionServiceInterface;

	@PreAuthorize("hasRole('AddRolePermission')")
	@PostMapping
	public ResponseEntity<?> addRoleToPermission(@RequestBody RolePermissionDto rolePermissionDto) {

		try {

			this.rolePermissionServiceInterface.addRolePermission(rolePermissionDto);
			return new ResponseEntity<>(new SuccessResponseDto("Permissions Added to Role",
					SuccessKeyConstant.ROLE_PERMISSION_M032402, rolePermissionDto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.INVALID_ROLE, ErrorKeyConstant.ROLE_PERMISSION_E032402),
					HttpStatus.BAD_REQUEST);
		}
	}

}
