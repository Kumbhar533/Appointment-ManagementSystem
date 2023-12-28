package com.appointment.management.serviceIntf;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.appointment.management.dto.IUserIdDto;
import com.appointment.management.dto.UpdateUserDto;
import com.appointment.management.iListDto.IListUserAppointmentsDto;
import com.appointment.management.iListDto.IUserListDto;

import jakarta.servlet.http.HttpServletResponse;

public interface UserServiceInterface {

	UpdateUserDto updateUserDto(UpdateUserDto dto, Long id, Long loggedUserId);

	public void deleteUser(Long Userid, Long loggedId);

	Page<IUserListDto> getAll(String pageNo, String pageSize);

	IUserIdDto getUserById(Long id);

	void bulkUploadUsers(MultipartFile multipartFile, Long userId) throws IOException, Exception;

	Page<IListUserAppointmentsDto> appointmentsList(HttpServletResponse response,
			Page<IListUserAppointmentsDto> userMeetings) throws IOException;

}
