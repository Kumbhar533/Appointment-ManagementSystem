package com.appointment.management.dto;

public class ErrorResponseDto {

	private String message;

	private String msgKey;

	public ErrorResponseDto(String message) {
		super();
		this.message = message;
	}

	public ErrorResponseDto(String message, String msgKey) {
		super();
		this.message = message;
		this.msgKey = msgKey;
	}

	public ErrorResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Object data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ErrorResponseDto(String message, String msgKey, Object data) {
		super();
		this.message = message;
		this.msgKey = msgKey;
		this.data = data;
	}

}
