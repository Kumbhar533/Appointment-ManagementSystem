package com.appointment.management.serviceIntf;

import com.appointment.management.dto.PasswordDto;

import jakarta.mail.MessagingException;

public interface EmailServiceInterface {

	public void sendMail(String emailTo, String subject) throws MessagingException;

	public int generateOtp(String email);

	public void setUserPassword(PasswordDto passwordDto);

}
