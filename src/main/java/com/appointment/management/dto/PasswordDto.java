package com.appointment.management.dto;

import jakarta.validation.constraints.Pattern;

public class PasswordDto {

	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Invalid password format*Invalid Format")
	private String password;

	private String email;

	private String otp;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public PasswordDto(
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Invalid password format*Invalid Format") String password,
			String email, String otp) {
		super();
		this.password = password;
		this.email = email;
		this.otp = otp;
	}

	public PasswordDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
