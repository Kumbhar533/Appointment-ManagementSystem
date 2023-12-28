package com.appointment.management.serviceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.appointment.management.dto.IUserIdDto;
import com.appointment.management.dto.UpdateUserDto;
import com.appointment.management.entities.BulkUploadEntity;
import com.appointment.management.entities.GenderEnum;
import com.appointment.management.entities.LoggerEntity;
import com.appointment.management.entities.RoleEntity;
import com.appointment.management.entities.RoleStatus;
import com.appointment.management.entities.UserEntity;
import com.appointment.management.entities.UserRoleEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.iListDto.IListUserAppointmentsDto;
import com.appointment.management.iListDto.IUserListDto;
import com.appointment.management.page.Pagination;
import com.appointment.management.repositories.BulkUploadRepository;
import com.appointment.management.repositories.LoggerRepository;
import com.appointment.management.repositories.RoleRepository;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.repositories.UserRoleRepository;
import com.appointment.management.serviceIntf.EmailServiceInterface;
import com.appointment.management.serviceIntf.UserServiceInterface;
import com.appointment.management.utils.ErrorMessageConstant;
import com.appointment.management.utils.Validators;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BulkUploadRepository bulkUploadRepository;

	@Autowired
	private LoggerRepository loggerRepository;

	@Autowired
	private EmailServiceInterface emailServiceInterface;

	@Override
	public UpdateUserDto updateUserDto(UpdateUserDto dto, Long id, Long loggedUserId) {

		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));

		userEntity.setUserName(dto.getUserName());
		userEntity.setGender(Enum.valueOf(GenderEnum.class, dto.getGender()));

		userEntity.setAddress(dto.getAddress());
		userEntity.setUpdatedBy(loggedUserId);
		userRepository.save(userEntity);

		if (dto.getRoleId() == null || dto.getRoleId().isEmpty()) {
			throw new RuntimeException("Role should required");
		}

		ArrayList<RoleStatus> roleIdsWithsatus = dto.getRoleId();

		ArrayList<UserRoleEntity> Userroles = new ArrayList<UserRoleEntity>();

		for (int i = 0; i < roleIdsWithsatus.size(); i++) {

			Long role_Id = dto.getRoleId().get(i).getId();

			RoleEntity roleEntity = roleRepository.findById(role_Id)
					.orElseThrow(() -> new RuntimeException("role not found for the id " + role_Id));

			UserRoleEntity userRoleEntity = userRoleRepository.findByUserRoleId(userEntity.getId(), role_Id);

			if (dto.getRoleId().get(i).getStatus() != 1 && dto.getRoleId().get(i).getStatus() != 3) {
				throw new RuntimeException("please enter the status either 1 or 3");
			}

			// 1 for add or 3 or delete
			Integer status = dto.getRoleId().get(i).getStatus();

			switch (status) {
			case 1:
				if (userRoleEntity == null) {
					userRoleEntity = new UserRoleEntity();
					userRoleEntity.setRoles(roleEntity);
					userRoleEntity.setUsers(userEntity);

				}
				userRoleEntity.setIsActive(true);
				;

				Userroles.add(userRoleEntity);
				break;
			case 3:
				if (userRoleEntity != null) {
					userRoleEntity.setIsActive(false);
					Userroles.add(userRoleEntity);
				} else {
					throw new ResourceNotFoundException(
							"UserRoleEntity not found for roleId: " + role_Id + " and userId: " + userEntity.getId());
				}
				break;

			}
		}
		userRoleRepository.saveAll(Userroles);
		return dto;
	}

	@Override
	public void deleteUser(Long Userid, Long Logged) {

		UserEntity userEntity = userRepository.findById(Userid)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND));

		if (userEntity != null) {
			userEntity.setIsActive(false);
			userEntity.setUpdatedBy(Logged);
		}

		userRepository.save(userEntity);
	}

	@Override
	public Page<IUserListDto> getAll(String pageNo, String pageSize) {
		Page<IUserListDto> page = null;
		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		page = this.userRepository.findByIsActiveTrueOrderByIdDesc(pageable, IUserListDto.class);

		return page;
	}

	@Override
	public IUserIdDto getUserById(Long id) {

		UserEntity userEntity = userRepository.findByIdAndIsActiveTrue(id);

		IUserIdDto iUserIdDto = new IUserIdDto();
		if (userEntity == null) {
			throw new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND);
		} else {

			iUserIdDto.setId(id);
			iUserIdDto.setUsername(userEntity.getUserName());
			iUserIdDto.setEmail(userEntity.getEmail());
			iUserIdDto.setGender(userEntity.getGender().toString());

			iUserIdDto.setAddress(userEntity.getAddress());
		}
		return iUserIdDto;
	}

	@Override
	public void bulkUploadUsers(MultipartFile multipartFile, Long userId) throws IOException, Exception {

		BulkUploadEntity bulkUploadEntity = new BulkUploadEntity();

		bulkUploadEntity.setFilename(multipartFile.getOriginalFilename());
		bulkUploadEntity.setUserId(userId);

		ArrayList<UserEntity> userEntityList = new ArrayList<UserEntity>();
		XSSFWorkbook workbook = null;
		int error = 0;
		int success = 0;

		try {
			workbook = new XSSFWorkbook(multipartFile.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				UserEntity entity = new UserEntity();
				try {
					XSSFRow row = worksheet.getRow(i);

					String username = getCellValueAsString(row.getCell(0));

					if (Validators.isValidforName(username)) {

						entity.setUserName(username);
					} else {
						throw new IllegalArgumentException("please enter valid name");
					}

					String email = getCellValueAsString(row.getCell(1));
					if (Validators.isValidforEmail(email)) {
						entity.setEmail(email);
					} else {
						throw new IllegalArgumentException("please enter valid email");
					}

					String gender = getCellValueAsString(row.getCell(2));
					entity.setGender(Enum.valueOf(GenderEnum.class, gender));

					String address = getCellValueAsString(row.getCell(3));

					entity.setAddress(address);
					entity.setCreatedBy(userId);
					success++;
					userEntityList.add(entity);

					emailServiceInterface.sendMail(email, "OTP For Password Set");

				} catch (Exception e) {

					error++;

					LoggerEntity errors = new LoggerEntity();
					errors.setErrorMesasage(e.getMessage());
					errors.setRow(i);
					loggerRepository.save(errors);
					continue;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		bulkUploadEntity.setErrorCount(error);
		bulkUploadEntity.setSuccessCount(success);

		bulkUploadRepository.save(bulkUploadEntity);
		userRepository.saveAll(userEntityList);

	}

	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}

	@Override
	public Page<IListUserAppointmentsDto> appointmentsList(HttpServletResponse response,
			Page<IListUserAppointmentsDto> userMeetings) throws IOException {
		StringBuilder builder = new StringBuilder();

		builder.append("appointment_id").append(",").append("appointment_date").append(",").append("manager")
				.append(",").append("response");
		builder.append('\n');

		for (IListUserAppointmentsDto meetings : userMeetings) {
			builder.append(meetings.getAppointmentId() != null ? meetings.getAppointmentId() : "").append(",")
					.append(meetings.getAppointmentDate() != null ? meetings.getAppointmentDate() : "").append(",")
					.append(meetings.getManager() != null ? meetings.getManager() : "").append(",")
					.append(meetings.getResponse() != null ? meetings.getResponse().name() : "").append(",");
			builder.append('\n');
		}
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=userList.csv");

		PrintWriter writer = response.getWriter();
		writer.write(builder.toString());
		writer.flush();
		writer.close();
		return userMeetings;
	}

}
