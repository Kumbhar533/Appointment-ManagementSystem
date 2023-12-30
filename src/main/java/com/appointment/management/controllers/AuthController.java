package com.appointment.management.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.management.dto.AddUserDto;
import com.appointment.management.dto.AuthResponseDto;
import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.dto.JwtRequest;
import com.appointment.management.dto.PasswordDto;
import com.appointment.management.dto.SuccessResponseDto;
import com.appointment.management.entities.UserEntity;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.serviceImpl.AuthInterfaceImpl;
import com.appointment.management.serviceIntf.AuthServiceInterface;
import com.appointment.management.serviceIntf.EmailServiceInterface;
import com.appointment.management.serviceIntf.JwtTokenUtilInterface;
import com.appointment.management.serviceIntf.RolePermissionServiceInterface;
import com.appointment.management.serviceIntf.UserRoleServiceInterface;
import com.appointment.management.utils.ApiUrls;
import com.appointment.management.utils.ErrorKeyConstant;
import com.appointment.management.utils.ErrorMessageConstant;
import com.appointment.management.utils.SuccessKeyConstant;
import com.appointment.management.utils.SuccessMessageConstant;
import com.appointment.management.utils.Validators;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrls.AUTH)
public class AuthController {

	@Autowired
	private AuthServiceInterface authServiceInterface;

	@Autowired
	private JwtTokenUtilInterface jwtTokenUtilInterface;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthInterfaceImpl authInterfaceImpl;

	@Autowired
	private EmailServiceInterface emailServiceInterface;

	@Autowired
	private RolePermissionServiceInterface rolePermissionServiceInterface;

	@Autowired
	private UserRoleServiceInterface userRoleServiceInterface;

	@PostMapping(ApiUrls.REGISTER)
	public ResponseEntity<?> addUser(@Valid @RequestBody AddUserDto adduser) {

		try {
			if (Validators.isValidforEmail(adduser.getEmail())) {
				if (Validators.isValidforEmployeeName(adduser.getUserName())) {

					UserEntity user = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(adduser.getEmail());
					if (user == null) {
						this.authServiceInterface.addUser(adduser);

						return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.USER_ADDED,
								SuccessKeyConstant.USER_M031104, adduser), HttpStatus.CREATED);
					} else {
						return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.EMAIL_ID_ALREADY_EXISTS,
								ErrorKeyConstant.USER_E031105), HttpStatus.BAD_REQUEST);
					}

				} else {
					return new ResponseEntity<>(
							new ErrorResponseDto(ErrorMessageConstant.INVALID_NAME, ErrorKeyConstant.USER_E031105),
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>(new ErrorResponseDto(ErrorMessageConstant.EMAIL_ID_ALREADY_EXISTS,
						ErrorKeyConstant.USER_E031105), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorKeyConstant.USER_E031102),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping(ApiUrls.LOGIN)
	private ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
		UserEntity userEntity = null;

		try {
			if (Validators.isValidforEmail(jwtRequest.getEmail())) {
				userEntity = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(jwtRequest.getEmail());
			}
			if (authInterfaceImpl.comparePassword(jwtRequest.getPassword(), userEntity.getPassword())) {
				final UserDetails userDetails = authInterfaceImpl.loadUserByUsername(jwtRequest.getEmail());
				final UserEntity entity = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(jwtRequest.getEmail());

				final String Token = this.jwtTokenUtilInterface.generateToken(userDetails);

				ArrayList<String> permissions = this.rolePermissionServiceInterface
						.getPermissionByUserId(userEntity.getId());

				ArrayList<String> roles = this.userRoleServiceInterface.getRoleByUserId(userEntity.getId());

				String refreshToken = this.jwtTokenUtilInterface.refreshToken(Token, userDetails);

				return new ResponseEntity<>(
						new SuccessResponseDto(SuccessMessageConstant.LOGIN_SUCCESSFULL,
								SuccessKeyConstant.USER_M031111, new AuthResponseDto(Token, refreshToken, permissions,
										userEntity.getEmail(), userEntity.getUserName(), userEntity.getId(), roles)),
						HttpStatus.OK);

			} else {
				return new ResponseEntity<>(
						new ErrorResponseDto(ErrorMessageConstant.INVALID_INFORMATION, ErrorKeyConstant.USER_E031103),
						HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ErrorKeyConstant.USER_E031103),
					HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping("/Password")
	public ResponseEntity<?> setPassword(@Valid @RequestBody PasswordDto pasword) {

		try {
			this.emailServiceInterface.setUserPassword(pasword);
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.PASSWORD_CREATED,
					SuccessKeyConstant.USER_PASSWORD_CREATED_E031401), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/resendOtp")
	public ResponseEntity<?> resendOtp(@RequestParam String email) {

		try {
			emailServiceInterface.sendMail(email, "OTP for password");
			return new ResponseEntity<>(new SuccessResponseDto(SuccessMessageConstant.OTP_SENDED_SUCCESSFULLY,
					SuccessKeyConstant.OTP_SENDED_E031501), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), ""), HttpStatus.BAD_REQUEST);
		}

	}

}
