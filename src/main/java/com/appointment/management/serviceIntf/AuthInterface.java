package com.appointment.management.serviceIntf;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthInterface {

	UserDetails loadUserByUsername(String email);

	boolean comparePassword(String email, String hashEmail);

	List<String> getUserPermission(Long id);

}
