package com.appointment.management.configuration;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticatioEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setContentType("application/json");

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		if (response.getHeader("Error") != null && response.getHeader("Error").equals("JWT expired")) {
			response.getOutputStream().println(
					"{\r\n" + "	\"message\": \"Jwt expired\",\r\n" + "	\"msgKey\": \"Jwt expired\"\r\n" + "}");

		} else {

			response.getOutputStream().println("{\r\n" + "	\"message\": \"Unauthorized user\",\r\n"
					+ "	\"msgKey\": \"Unauthorized\"\r\n" + "}");
		}

	}

}
