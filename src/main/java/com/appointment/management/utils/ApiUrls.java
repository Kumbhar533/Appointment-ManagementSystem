package com.appointment.management.utils;

public class ApiUrls {

	public static final String DESIGNATION = "/designation";
	public static final String API = "/api";
	public static final String GET_ALL = "/all";
	public static final String USERS = "/users";
	public static final String PERMISSIONS = "/permissions";
	public static final String ROLES = "/roles";
	public static final String TOPIC = "/topics";
	public static final String USERTECHNOLOGIES = "/usertechnologies";
	public static final String TECHNOLOGIES = "/technologies";
	public static final String ROLEPERMISSION = "/role-permission";
	public static final String AUTH = "/auth";
	public static final String LOGIN = "/login";
	public static final String REGISTER = "/register";
	public static final String FORGOT_PASSWORD = AUTH + "/forgot-password";
	public static final String FORGOT_PASSWORD_CONFIRM = AUTH + "/forgot-password/confirm";
	public static final String LOGIN_WITH_OTP = AUTH + "/login-with-otp";
	public static final String LOGIN_WITH_OTP_CONFIRM = AUTH + "/login-with-otp/confirm";
	public static final String UPDATE_EMAIL = AUTH + "/update-email";
	public static final String UPDATE_EMAIL_CONFIRM = AUTH + "/update-email/confirm";
	public static final String CATEGORY = "/category";
	public static final String TASKS = "/tasks";
	public static final String ASSIGNMENT = "/assignment";
	public static final String SUB_TOPIC = "/sub-topic";
	public static final String USER_TOPIC_HISTORY = "/user-topic-history";
	public static final String USER_TOPIC = "/user-topic";
	public static final String USER_MENTOR = "/user-mentor";
	public static final String IP_ADDRESS = "/ipAddress";
	public static final String REFRESH_TOKEN = AUTH + "/refresh-token";
	public static final String DOWNLOAD_FILE = "/downloadFile/{fileName:.+}";

	public static final String USER_TOPIC_REVIEW = "/user_topic_review";

	public static final String USER_ASSIGNMENT = "/user-assignment";
	public static final String USER_ASSIGNMENT_HISRORY = "/user-assignment-history";
	public static final String PROJECT = "/projects";
	public static final String USER_REVIEW = "/user-review";
	public static final String USER_PROJECT = "/user-projects";
	public static final String USER_TASK = "/user-task";
	public static final String TASK_REVIEW = "/task-review";
	public static final String MODULES = "/modules";
	public static final String DASHBOARD = "/dashboard";
	public static final String USER_SESSION = "/user-session";
	public static final String SESSIONS = "/sessions";
	public static final String ONBOARD = AUTH + "/onboard";
	public static final String BULK_UPLOAD = "/bulk-upload";
	public static final String COUNTRY = "/country";
	public static final String STATE = "/state";
	public static final String USER_ADDRESS = "/user-address";
	public static final String CITY = "/city";
	public static final String MAILTEMPLATE = "/mailTemplate";
	public static final String BRANCH = "/branch";
	public static final String[] SWAGGER_URLS = { "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**",
			"/swagger-ui/**", "/webjars/**", "/swagger-ui/index.html" };

	public static final String[] URLS_WITHOUT_HEADER = { ApiUrls.AUTH + ApiUrls.LOGIN, ApiUrls.AUTH + ApiUrls.REGISTER,
			ApiUrls.AUTH + ApiUrls.FORGOT_PASSWORD, ApiUrls.AUTH + ApiUrls.FORGOT_PASSWORD_CONFIRM,
			ApiUrls.AUTH + ApiUrls.LOGIN_WITH_OTP, ApiUrls.AUTH + ApiUrls.LOGIN_WITH_OTP_CONFIRM,
			ApiUrls.AUTH + ApiUrls.ONBOARD, ApiUrls.AUTH + ApiUrls.REFRESH_TOKEN };

}
