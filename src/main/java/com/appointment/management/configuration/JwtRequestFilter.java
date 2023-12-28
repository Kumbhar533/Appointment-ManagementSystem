package com.appointment.management.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.appointment.management.entities.UserEntity;
import com.appointment.management.exceptions.ResourceNotFoundException;
import com.appointment.management.repositories.UserRepository;
import com.appointment.management.serviceImpl.AuthInterfaceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthInterfaceImpl authInterfaceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String email = null;
		String jwtToken = null;

		try {
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")
					&& jwtTokenUtil.getTokenType(requestTokenHeader.substring(7)).equals("access")) {

				jwtToken = requestTokenHeader.substring(7);
				email = jwtTokenUtil.getUsernameFromToken(jwtToken);
				UserEntity userEntity = userRepository.findByEmailIgnoreCaseAndIsActiveTrue(email);
				if (userEntity == null) {
					throw new ResourceNotFoundException("Invalid user");
				}
			}
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			response.setHeader("Error", "JWT expired");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());

		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.authInterfaceImpl.loadUserByUsername(email);

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);

	}

}
