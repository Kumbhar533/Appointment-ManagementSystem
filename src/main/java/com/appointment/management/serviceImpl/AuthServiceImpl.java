package com.appointment.management.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointment.management.dto.AddUserDto;
import com.appointment.management.entities.GenderEnum;
import com.appointment.management.entities.UserEntity;
import com.appointment.management.entities.UserRoleEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.repositories.RoleRepository;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.repositories.UserRoleRepository;
import com.appointment.management.serviceIntf.AuthServiceInterface;
import com.appointment.management.utils.ErrorMessageConstant;

@Service
public class AuthServiceImpl implements AuthServiceInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public AddUserDto addUser(AddUserDto add) {

		ArrayList<Long> roles = add.getRoleId();

		if (roles == null) {
			throw new ResourceNotFoundException("please enter valid role!");
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(add.getUserName());
		userEntity.setEmail(add.getEmail());
		userEntity.setGender(Enum.valueOf(GenderEnum.class, add.getGender()));
		userEntity.setAddress(add.getAddress());

		String encodePassword = passwordEncoder.encode(add.getPassword());
		userEntity.setPassword(encodePassword);
		UserEntity save = userRepository.save(userEntity);

		List<UserRoleEntity> userRoleEntities = roles.stream()
				.map(roleId -> roleRepository.findById(roleId)
						.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstant.ROLE_NOT_FOUND)))
				.map(roleEntity -> {
					UserRoleEntity userRoleEntity = new UserRoleEntity();
					userRoleEntity.setRoles(roleEntity);
					userRoleEntity.setUsers(save);
					return userRoleEntity;
				}).collect(Collectors.toList());

		userRoleRepository.saveAll(userRoleEntities);

		return add;
	}

}
