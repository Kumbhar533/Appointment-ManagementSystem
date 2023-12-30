package com.appointment.management.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointment.management.entities.UserEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.serviceIntf.AuthInterface;
import com.appointment.management.serviceIntf.RolePermissionServiceInterface;
import com.appointment.management.utils.CacheOperation;
import com.appointment.management.utils.ErrorMessageConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthInterfaceImpl implements Serializable, UserDetailsService, AuthInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4372565601930807631L;

	@Autowired
	private CacheOperation cache;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RolePermissionServiceInterface RolePermissionServiceInterface;

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean comparePassword(String password, String hashpassword) {

		return passwordEncoder.matches(password, hashpassword);

	}

	@Override
	public List<String> getUserPermission(Long id) {
		ArrayList<String> permissions = null;

		permissions = RolePermissionServiceInterface.getPermissionByUserId(id);
		return permissions;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = new UserEntity();

		// username = email

		if (!cache.isKeyExist(username, username)) {

			userEntity = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(username);
			cache.addInCache(username, username, userEntity.toString());
		}

		else {
			String jsonString = (String) cache.getFromCache(username, username);

			try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = mapper.readValue(jsonString, Map.class);
				userEntity.setPassword((String) map.get("password"));
				userEntity.setEmail((String) map.get("email"));
				userEntity.setId(((Integer) map.get("id")).longValue());
			} catch (Exception e) {
				e.getMessage();
			}
		}
		if (userEntity.getEmail().isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessageConstant.USER_NOT_FOUND);
		}

		return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(),
				getAuthority(userEntity));
	}

	private ArrayList<SimpleGrantedAuthority> getAuthority(UserEntity user) {
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

		if ((user.getId() + "permission") != null) {
			ArrayList<SimpleGrantedAuthority> authorities1 = new ArrayList<>();

			ArrayList<String> permissions = RolePermissionServiceInterface.getPermissionByUserId(user.getId());

			permissions.forEach(e -> {
				authorities1.add(new SimpleGrantedAuthority("ROLE_" + e));

			});
			authorities = authorities1;

		}
		return authorities;

	}

}
