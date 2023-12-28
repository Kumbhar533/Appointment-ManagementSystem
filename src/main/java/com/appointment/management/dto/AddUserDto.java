package com.appointment.management.dto;

import java.util.ArrayList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddUserDto {

	private String userName;

	@NotBlank(message = "email is Required*emailNameRequired")
	@NotEmpty(message = "email is Required*emailNameRequired")
	@NotNull(message = "email is Required*emailRequired")
	private String email;

	private String gender;

	private ArrayList<Long> roleId;

	private String address;

	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Invalid password format*Invalid Format")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ArrayList<Long> getRoleId() {
		return roleId;
	}

	public void setRoleId(ArrayList<Long> roleId) {
		this.roleId = roleId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AddUserDto(String userName,
			@NotBlank(message = "email is Required*emailNameRequired") @NotEmpty(message = "email is Required*emailNameRequired") @NotNull(message = "email is Required*emailRequired") String email,
			String gender, ArrayList<Long> roleId, String address, String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.gender = gender;
		this.roleId = roleId;
		this.address = address;
		this.password = password;
	}

	public AddUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
