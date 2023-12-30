package com.appointment.management.interceptors;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.appointment.management.entities.UserEntity;
import com.appointment.management.entities.UserRoleEntity;
import com.appointment.management.repositories.RolePermissionRepository;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.repositories.UserRoleRepository;
import com.appointment.management.serviceIntf.JwtTokenUtilInterface;
import com.appointment.management.utils.ApiUrls;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthLogger implements HandlerInterceptor {

	@Autowired
	private JwtTokenUtilInterface jwtTokenUtilInterface;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authHeader = request.getHeader("Authorization");
		String tokenString = (null != authHeader) ? authHeader.split(" ")[1] : null;

		ArrayList<String> urlsWithoutHeader = new ArrayList<>(Arrays.asList(ApiUrls.URLS_WITHOUT_HEADER));
		final String requestUrl = request.getRequestURI();

		if (!urlsWithoutHeader.contains(requestUrl)) {
			if (tokenString != null) {
				final String emailString = jwtTokenUtilInterface.getUsernameFromToken(tokenString);

				UserEntity userEntity = this.userRepository.findByEmail(emailString);
				if (userEntity != null) {

					ArrayList<UserRoleEntity> userRoleEntity = this.userRoleRepository
							.getRolesOfUser(userEntity.getId());

					ArrayList<String> roles = new ArrayList<String>();

					for (int i = 0; i < userRoleEntity.size(); i++) {
						roles.add(userRoleEntity.get(i).getRoles().getRoleName());
					}

					ArrayList<String> rolePermissionEntities = this.rolePermissionRepository
							.getPermissionOfUser(userEntity.getId());

					request.setAttribute("X-user-permissions", rolePermissionEntities);
					request.setAttribute("X-user-roles", roles);
					request.setAttribute("X-user-id", userEntity.getId());
				}

			}
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);// return true to proceed the request to
																				// controller
	}

}
