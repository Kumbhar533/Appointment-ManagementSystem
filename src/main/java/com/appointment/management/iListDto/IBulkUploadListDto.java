package com.appointment.management.iListDto;

public interface IBulkUploadListDto {

	public Long getId();

	public String getFileName();

	public Integer getErrorCount();

	public Integer getSuccessCount();

	public String getUploadedBy();

	public Integer getTotalRecords();

}
