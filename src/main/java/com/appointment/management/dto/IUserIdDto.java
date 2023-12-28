package com.appointment.management.dto;

public class IUserIdDto {

	public Long id;

	public String username;

	public String gender;

	public String email;

	public String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public IUserIdDto(Long id, String username, String gender, String email, String address) {
		super();
		this.id = id;
		this.username = username;
		this.gender = gender;
		this.email = email;
		this.address = address;
	}

	public IUserIdDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
