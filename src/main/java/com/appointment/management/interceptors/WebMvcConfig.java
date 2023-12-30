package com.appointment.management.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private AuthLogger authLogger;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {// matching all the incoming request

		registry.addInterceptor(authLogger);

	}

}
