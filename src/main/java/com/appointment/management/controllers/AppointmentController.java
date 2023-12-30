package com.appointment.management.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.appointment.management.dto.AppointmentDto;
import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.dto.ListResponseDto;
import com.appointment.management.dto.PaginationResponse;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.iListDto.IListAppointmentDto;
import com.appointment.management.iListDto.IListUserAppointmentsDto;
import com.appointment.management.serviceIntf.AppointmentServiceInf;
import com.appointment.management.serviceIntf.UserServiceInterface;
import com.appointment.management.utils.Constant;
import com.appointment.management.utils.ErrorKeyConstant;
import com.appointment.management.utils.ErrorMessageConstant;
import com.appointment.management.utils.GlobalFunctions;
import com.appointment.management.utils.SuccessKeyConstant;
import com.appointment.management.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/appointment")
@SecurityRequirement(name = "jwt")
public class AppointmentController {

	@Autowired
	private AppointmentServiceInf appointmentServiceInf;

	@Autowired
	private UserServiceInterface serviceInterface;

	@PreAuthorize("hasRole('CreateAppointment')")
	@PostMapping
	public ResponseEntity<?> createAppointment(@RequestBody AppointmentDto appointment,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {

			this.appointmentServiceInf.appointment(appointment, userId);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.APPOINTMENT_SEHDULED,
					SuccessKeyConstant.APPONTMENT_M031201, appointment), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.APPOINTMENT_NOT_SEHDULED,
					ErrorKeyConstant.APPOINTMENT_E031201), HttpStatus.BAD_REQUEST);

		}

	}

	// manager can see her booked appointments
	@PreAuthorize("hasRole('AppointmentList')")
	@GetMapping("/getAllAppointments")
	public ResponseEntity<?> getAllAppointments(
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {

			Page<IListAppointmentDto> appointmentsList = this.appointmentServiceInf.getAllApointment(userId, pageNo,
					pageSize);

			PaginationResponse paginationResponse = new PaginationResponse();

			paginationResponse.setPageSize(appointmentsList.getSize());
			paginationResponse.setTotal(appointmentsList.getTotalElements());
			paginationResponse.setPageNumber(appointmentsList.getNumber() + 1);

			return new ResponseEntity<>(new ListResponseDto(appointmentsList.getContent(), paginationResponse),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.APPOINTMENTS_NOT_FETCHED,
					ErrorKeyConstant.APPOINTMENT_E031202), HttpStatus.BAD_REQUEST);
		}

	}

	// user can see her Appointments
	@PreAuthorize("hasRole('UserAppointments')")
	@GetMapping("/usersAppointments")
	public ResponseEntity<?> getAllUserAppointments(
			@RequestParam(defaultValue = Constant.DEFAULT_PAGENUMBER, value = Constant.PAGENUMBER) String pageNo,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGESIZE, value = Constant.PAGESIZE) String pageSize,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(name = Constant.START_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name = Constant.END_DATE, required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(defaultValue = "false") Boolean export, HttpServletResponse response) {
		try {

			if (startDate == null) {

				startDate = LocalDate.now();
			}

			if (endDate == null) {
				// If endDate is not provided, set it to today
				endDate = LocalDate.now();
			}
			String start = String.valueOf(startDate);
			String end = String.valueOf(endDate);

			if (export) {
				Page<IListUserAppointmentsDto> appointmentsList = this.appointmentServiceInf.getUserAppointments(userId,
						pageNo, pageSize, start, end);
				response.setContentType("text/csv");
				response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");

				serviceInterface.appointmentsList(response, appointmentsList);
				return ResponseEntity.ok().build();
			} else {
				Page<IListUserAppointmentsDto> appointmentsList = this.appointmentServiceInf.getUserAppointments(userId,
						pageNo, pageSize, start, end);
				PaginationResponse paginationResponse = new PaginationResponse();

				paginationResponse.setPageSize(appointmentsList.getSize());
				paginationResponse.setTotal(appointmentsList.getTotalElements());
				paginationResponse.setPageNumber(appointmentsList.getNumber() + 1);

				return new ResponseEntity<>(new ListResponseDto(appointmentsList.getContent(), paginationResponse),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.APPOINTMENTS_NOT_FETCHED,
					ErrorKeyConstant.APPOINTMENT_E031202), HttpStatus.BAD_REQUEST);
		}

	}

	// user can accept and decline
	@PreAuthorize("hasRole('UpdateAppointment')")
	@PutMapping("/{appointmentId}")
	public ResponseEntity<?> AcceptDeniedAppointment(@PathVariable Long appointmentId,
			@RequestParam(value = "response") String response) {

		try {
			this.appointmentServiceInf.AcceptOrDeclineAppointment(appointmentId, response);
			if (response.equalsIgnoreCase("ACCEPTED")) {

				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.APPOINTMENT_ACCEPT,
						SuccessKeyConstant.APPONTMENT_M031202), HttpStatus.ACCEPTED);
			} else if (response.equalsIgnoreCase("DECLINED")) {

				return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.APPOINTMENT_DENIED,
						SuccessKeyConstant.APPONTMENT_M031203), HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(new ErrorResponseDto("Invalid response provided."), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("\"An error occurred while processing the appointment.\""),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PreAuthorize("hasRole('DeleteAppointment')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAppointment(@PathVariable Long id,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			this.appointmentServiceInf.deleteAppointment(userId, id);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.APPOINTMENT_DELETED,
					SuccessKeyConstant.APPONTMENT_M031204), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.APPOINTMENTS_NOT_FOUND,
					ErrorKeyConstant.APPOINTMENT_E031202), HttpStatus.BAD_REQUEST);
		}

	}
}
