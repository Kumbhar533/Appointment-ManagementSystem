package com.appointment.management.dto;

import java.util.ArrayList;

import com.appointment.management.entities.RoleStatus;

public class UpdateUserDto {

	private String userName;

	private String gender;

	private ArrayList<RoleStatus> roleId;

	private String address;

	public UpdateUserDto(String userName, String gender, ArrayList<RoleStatus> roleId, String address) {
		super();
		this.userName = userName;
		this.gender = gender;
		this.roleId = roleId;
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public ArrayList<RoleStatus> getRoleId() {
		return roleId;
	}

	public void setRoleId(ArrayList<RoleStatus> roleId) {
		this.roleId = roleId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UpdateUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
