package com.appointment.management.dto;

import java.util.Date;

public class AppointmentDto {

	private Long developer;

	private Date appointmentDate;

	public AppointmentDto(Long developer, Date appointmentDate) {
		super();
		this.developer = developer;
		this.appointmentDate = appointmentDate;
	}

	public Long getDeveloper() {
		return developer;
	}

	public void setDeveloper(Long developer) {
		this.developer = developer;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public AppointmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
