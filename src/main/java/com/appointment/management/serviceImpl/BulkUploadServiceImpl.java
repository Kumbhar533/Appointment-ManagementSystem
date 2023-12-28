package com.appointment.management.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appointment.management.iListDto.IBulkUploadListDto;
import com.appointment.management.repositories.BulkUploadRepository;
import com.appointment.management.serviceIntf.BulkServiceInterface;

@Service
public class BulkUploadServiceImpl implements BulkServiceInterface {

	@Autowired
	private BulkUploadRepository bulkUploadRepository;

	@Override
	public List<IBulkUploadListDto> getAllBulkUploadList() {

		List<IBulkUploadListDto> uploadedFiles = this.bulkUploadRepository.findAllBulkUploades();

		return uploadedFiles;
	}

}
