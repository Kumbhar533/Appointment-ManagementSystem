package com.appointment.management.dto;

public class BlockUserDto {

	private Long blockedEntityId;

	private String reason;

	private boolean blockStatus;

	public BlockUserDto(Long blockedEntityId, String reason, boolean blockStatus) {
		super();
		this.blockedEntityId = blockedEntityId;
		this.reason = reason;
		this.blockStatus = blockStatus;
	}

	public Long getBlockedEntityId() {
		return blockedEntityId;
	}

	public void setBlockedEntityId(Long blockedEntityId) {
		this.blockedEntityId = blockedEntityId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isBlockStatus() {
		return blockStatus;
	}

	public void setBlockStatus(boolean blockStatus) {
		this.blockStatus = blockStatus;
	}

	public BlockUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
