package com.appointment.management.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AppointmentDto {

	private Long developer;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime appointmentDate;

	public Long getDeveloper() {
		return developer;
	}

	public AppointmentDto(Long developer, LocalDateTime appointmentDate) {
		super();
		this.developer = developer;
		this.appointmentDate = appointmentDate;
	}

	public void setDeveloper(Long developer) {
		this.developer = developer;
	}

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public AppointmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
