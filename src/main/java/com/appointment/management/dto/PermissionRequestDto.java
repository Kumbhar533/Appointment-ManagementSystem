package com.appointment.management.dto;

public class PermissionRequestDto {

	private String actionName;

	private String baseUrl;

	private String method;

	private String description;

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PermissionRequestDto(String actionName, String baseUrl, String method, String description) {
		super();
		this.actionName = actionName;
		this.baseUrl = baseUrl;
		this.method = method;
		this.description = description;
	}

	public PermissionRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
