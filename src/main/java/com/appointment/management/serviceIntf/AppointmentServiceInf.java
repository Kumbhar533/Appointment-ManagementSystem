package com.appointment.management.serviceIntf;

import org.springframework.data.domain.Page;

import com.appointment.management.dto.AppointmentDto;
import com.appointment.management.iListDto.IListAppointmentDto;
import com.appointment.management.iListDto.IListUserAppointmentsDto;

public interface AppointmentServiceInf {

	AppointmentDto appointment(AppointmentDto appointmentDto, Long loggedUser);

	Page<IListAppointmentDto> getAllApointment(Long managerId, String pageNo, String PageSize);

	Page<IListUserAppointmentsDto> getUserAppointments(Long userId, String pageNo, String PageSize, String startdate,
			String enddate);

	public void AcceptOrDeclineAppointment(Long appointmentId, String response);

	public void deleteAppointment(Long id, Long loggedId);

}
