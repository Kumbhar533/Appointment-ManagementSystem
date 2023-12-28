package com.appointment.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.appointment.management.dto.AppointmentDto;
import com.appointment.management.entities.AppointmentEntity;
import com.appointment.management.entities.AppointmentResponseEntity;
import com.appointment.management.entities.AppointmentResponseEnum;
import com.appointment.management.entities.BlockedUserEntity;
import com.appointment.management.entities.UserEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.iListDto.IListAppointmentDto;
import com.appointment.management.iListDto.IListUserAppointmentsDto;
import com.appointment.management.page.Pagination;
import com.appointment.management.repositories.AppointmentRepository;
import com.appointment.management.repositories.AppointmentResponseReposistory;
import com.appointment.management.repositories.BlockedRepository;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.serviceIntf.AppointmentServiceInf;
import com.appointment.management.utils.ErrorMessageConstant;

@Service
public class AppointmentServiceImpl implements AppointmentServiceInf {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppointmentResponseReposistory appointmentResponseReposistory;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private BlockedRepository blockedRepository;

	@Override
	public AppointmentDto appointment(AppointmentDto appointmentDto, Long loggedUser) {

		AppointmentEntity appointmentEntity = new AppointmentEntity();
		AppointmentResponseEntity appointmentResponseEntity = new AppointmentResponseEntity();

		BlockedUserEntity checkBlockedUser = blockedRepository.findByUserId(appointmentDto.getDeveloper(), loggedUser);

		UserEntity manager = userRepository.findById(loggedUser)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));

		UserEntity developer = userRepository.findById(appointmentDto.getDeveloper())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));

		if (checkBlockedUser == null) {
			appointmentEntity.setDeveloper(developer);

			appointmentEntity.setManager(manager);

			appointmentEntity.setCreatedBy(loggedUser);

			appointmentEntity.setAppointmentDate(appointmentDto.getAppointmentDate());

			appointmentEntity.setStatus(Enum.valueOf(AppointmentResponseEnum.class, "PENDING"));

			appointmentRepository.save(appointmentEntity);

			appointmentResponseEntity.setUserEntity(developer);

			appointmentResponseEntity.setResponse(Enum.valueOf(AppointmentResponseEnum.class, "PENDING"));

			appointmentResponseEntity.setAppointmentEntity(appointmentEntity);

			appointmentResponseReposistory.save(appointmentResponseEntity);
		} else {
			throw new RuntimeException("This is blocked user");
		}
		return appointmentDto;
	}

	@Override
	public Page<IListAppointmentDto> getAllApointment(Long managerId, String pageNo, String pageSize) {

		this.userRepository.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		Page<IListAppointmentDto> page = this.appointmentRepository.findAllAppointmentsByManagerId(pageable, managerId);
		return page;
	}

	@Override
	public Page<IListUserAppointmentsDto> getUserAppointments(Long userId, String pageNo, String PageSize,
			String startdate, String enddate) {
		Pageable pageable = null;

		pageable = new Pagination().getPagination(pageNo, PageSize);

		Page<IListUserAppointmentsDto> page = this.appointmentRepository.findUserAppointments(pageable, userId,
				startdate, enddate);

		return page;
	}

	@Override
	public void AcceptOrDeclineAppointment(Long appointmentId, String response) {

		AppointmentEntity appointmentEntity = this.appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.APPOINTMENTS_NOT_FOUND));

		AppointmentResponseEntity appointmentResponseEntity = appointmentResponseReposistory.findById(appointmentId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.APPOINTMENTS_NOT_FOUND));

		appointmentResponseEntity.setResponse(Enum.valueOf(AppointmentResponseEnum.class, response));

		if (response.equalsIgnoreCase("DECLINED")) {
			appointmentEntity.setIsActive(false);

		}
		appointmentEntity.setStatus(Enum.valueOf(AppointmentResponseEnum.class, response));

		appointmentResponseReposistory.save(appointmentResponseEntity);

		appointmentRepository.save(appointmentEntity);
	}

	@Override
	public void deleteAppointment(Long id, Long loggedId) {

		AppointmentEntity appointmentEntity = this.appointmentRepository.findByIdAndIsActiveTrue(id);

		if (appointmentEntity != null) {
			appointmentEntity.setIsActive(false);
		}
		appointmentEntity.setUpdatedBy(loggedId);

		appointmentRepository.save(appointmentEntity);
	}

}
