package com.appointment.management.serviceIntf;

import java.util.Date;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtTokenUtilInterface {

	public String getEmailFromToken(String token);

	public String getUsernameFromToken(String token);

	public Date getExpirationDateFromToken(String token);

	public Claims getAllClaimsFromToken(String token);

	public Boolean isTokenExpired(String token);

	public String generateToken(UserDetails userDetails);

	public String doGenerateToken(Map<String, Object> claims, String subject);

	public Boolean validateToken(String token, UserDetails userDetails);

	public String refreshToken(String token, UserDetails userDetails);

	public Date calculateExpirationDate(Date createdDate);

	public boolean canTokenBeRefreshed(String token);

	public String getTokenType(String token) throws Exception;

}
