package com.appointment.management.iListDto;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface IListAppointmentDto {

	public Long getAppointmentId();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public String getAppointmentDate();

	public String getDeveloper();

	public String getResponse();

}
