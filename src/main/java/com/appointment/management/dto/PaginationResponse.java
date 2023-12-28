package com.appointment.management.dto;

public class PaginationResponse {

	private int pageNumber;
	private int pageSize;
	private Long total;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PaginationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public PaginationResponse(int pageNumber, int pageSize, Long total) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.total = total;
	}

}
