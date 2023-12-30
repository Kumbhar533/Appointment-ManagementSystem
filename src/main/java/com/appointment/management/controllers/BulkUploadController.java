package com.appointment.management.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.iListDto.IBulkUploadListDto;
import com.appointment.management.serviceImpl.FileStorageService;
import com.appointment.management.serviceIntf.BulkServiceInterface;
import com.appointment.management.serviceIntf.UserServiceInterface;
import com.appointment.management.utils.GlobalFunctions;
import com.appointment.management.utils.SuccessKeyConstant;
import com.appointment.management.utils.SuccessMessageConstant;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/bulkUpload")
@SecurityRequirement(name = "jwt")
public class BulkUploadController {

	@Value("${file.storage.location}")
	public String fileStorageLocation;

	@Autowired
	private UserServiceInterface serviceInterface;

	@Autowired
	private BulkServiceInterface bulkServiceInterface;

	@Autowired
	private FileStorageService fileStorageService;

	@PreAuthorize("hasRole('BulkUpload')")
	@PostMapping
	public ResponseEntity<?> bulkUpload(@RequestParam(name = "file", required = true) MultipartFile file,
			@RequestAttribute(GlobalFunctions.CUSTUM_ATTRIBUTE_USER_ID) Long userId) {

		try {
			serviceInterface.bulkUploadUsers(file, userId);

			// Assuming you have this service
			String filename = file.getOriginalFilename(); // Or generate a unique filename
			fileStorageService.storeFile(file, filename);
			return new ResponseEntity<>(new SuccessResponseDto("Success", "Success"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('BulkUploadList')")
	@GetMapping("/all")
	public ResponseEntity<?> getAllBulkUpload() {

		try {
			List<IBulkUploadListDto> allBulkUploadList = this.bulkServiceInterface.getAllBulkUploadList();
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.BULK_DATA_FETCHED,
					SuccessKeyConstant.BULK_DATA_E031601, allBulkUploadList), HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('BulkUploadDownload')")
	@GetMapping("/download")
	public ResponseEntity<?> downloadFile(@RequestParam String fileName) {
		try {
			Path filePath = Paths.get(fileStorageLocation, fileName);

			// Check if the file exists
			if (!Files.exists(filePath)) {
				return new ResponseEntity<>(new ErrorResponseDto("File not found"), HttpStatus.NOT_FOUND);
			}

			Resource resource = new FileSystemResource(filePath);

			// Determine the appropriate MediaType based on the file extension
			MediaType mediaType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);

			return ResponseEntity.ok().contentType(mediaType)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
