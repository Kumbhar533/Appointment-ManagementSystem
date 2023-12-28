package com.appointment.management.entities;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blocked_user")
public class BlockedUserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6215635804724610136L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "blocking_id")
	private Long blockingId;

	@Column(name = "blocked_Entity_Id")
	private Long blockedEntityId;

	@Column(name = "blocking_date")
	@CreationTimestamp
	private Date blockedDate;

	@Column(name = "reason")
	private String reasonForBlock;

	// take boolean
	@Column(name = "block_status")
	private boolean blockStatus = true;

	public BlockedUserEntity(Long id, Long blockingId, Long blockedEntityId, Date blockedDate, String reasonForBlock,
			boolean blockStatus) {
		super();
		this.id = id;
		this.blockingId = blockingId;
		this.blockedEntityId = blockedEntityId;
		this.blockedDate = blockedDate;
		this.reasonForBlock = reasonForBlock;
		this.blockStatus = blockStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBlockingId() {
		return blockingId;
	}

	public void setBlockingId(Long blockingId) {
		this.blockingId = blockingId;
	}

	public Long getBlockedEntityId() {
		return blockedEntityId;
	}

	public void setBlockedEntityId(Long blockedEntityId) {
		this.blockedEntityId = blockedEntityId;
	}

	public Date getBlockedDate() {
		return blockedDate;
	}

	public void setBlockedDate(Date blockedDate) {
		this.blockedDate = blockedDate;
	}

	public String getReasonForBlock() {
		return reasonForBlock;
	}

	public void setReasonForBlock(String reasonForBlock) {
		this.reasonForBlock = reasonForBlock;
	}

	public boolean isBlockStatus() {
		return blockStatus;
	}

	public void setBlockStatus(boolean blockStatus) {
		this.blockStatus = blockStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BlockedUserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
