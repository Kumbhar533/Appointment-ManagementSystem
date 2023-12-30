package com.appointment.management.iListDto;

import java.util.Date;

import com.appointment.management.entities.AppointmentResponseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public interface IListUserAppointmentsDto {

	public Long getAppointmentId();

	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", shape = Shape.STRING)
	public Date getAppointmentDate();

	public String getManager();

	public AppointmentResponseEnum getResponse();
}
