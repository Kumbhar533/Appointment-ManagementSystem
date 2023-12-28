package com.appointment.management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.appointment.management.dto.RoleDto;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.iListDto.IListRoleDto;
import com.appointment.management.iListDto.IListRolePermissionDto;
import com.appointment.management.serviceIntf.RoleServiceInterface;
import com.appointment.management.utils.Constant;
import com.appointment.management.utils.ErrorKeyConstant;
import com.appointment.management.utils.ErrorMessageConstant;
import com.appointment.management.utils.GlobalFunctions;
import com.appointment.management.utils.SuccessKeyConstant;
import com.appointment.management.utils.SuccessMessageConstant;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleServiceInterface roleServiceInterface;

	@PostMapping
	public ResponseEntity<?> addRole(@RequestBody RoleDto roleDto) {

		try {
			this.roleServiceInterface.role(roleDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(SuccessMessageConstant.ROLE_ADDED, SuccessKeyConstant.ROLE_M032204, roleDto),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.INVALID_ROLE, ErrorKeyConstant.ROLE_E032203),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRole(@PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long loggedId, @RequestBody RoleDto roledto) {
		try {

			this.roleServiceInterface.updateRole(roledto, loggedId, id);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.ROLE_UPDATED,
					SuccessKeyConstant.ROLE_M032203, roledto), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.ROLE_NOT_FOUND, ErrorKeyConstant.ROLE_E032201),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> allRoles(
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize) {

		try {

			Page<IListRoleDto> roles = this.roleServiceInterface.getAllRoles(pageNo, pageSize);

			PaginationResponse response = new PaginationResponse();
			response.setPageSize(roles.getSize());
			response.setTotal(roles.getTotalElements());
			response.setPageNumber(roles.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(roles.getContent(), response), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.ROLE_NOT_FOUND, ErrorKeyConstant.ROLE_E032201),
					HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping("/permissions/{id}")
	public ResponseEntity<?> permissionsByRole(@PathVariable(value = "id") Long id) {
		try {
			List<IListRolePermissionDto> permissions = this.roleServiceInterface.getPermissionsByRole(id);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.FETCH_USER_ROLE_PERMISSION,
					SuccessKeyConstant.ROLE_PERMISSION_M032401, permissions), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ErrorResponseDto(ErrorMessageConstant.ROLE_NOT_FOUND, ErrorKeyConstant.ROLE_E032201),
					HttpStatus.BAD_REQUEST);
		}

	}

}
