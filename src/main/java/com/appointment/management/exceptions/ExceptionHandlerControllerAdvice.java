package com.appointment.management.exceptions;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.appointment.management.dto.ErrorResponseDto;
import com.appointment.management.entities.ErrorLoggerEntity;
import com.appointment.management.entities.MethodEnum;
import com.appointment.management.repositories.ErrorLoggerRepository;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@Autowired
	private ErrorLoggerRepository errorLoggerRepository;

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleResourceNotFoundException(final ResourceNotFoundException ex,
			final HttpServletRequest request) {

		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(ex.getMessage());
		response.setRequestedURI(request.getRequestURI());
		return response;

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public @ResponseBody ExceptionResponse handleDataIntegrityViolationException(
			final DataIntegrityViolationException ex) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage("Data is present already !!");
		error.setRequestedURI("Data_Redendency");
		return error;

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponseDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<String>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {

			errors.add(error.getDefaultMessage());
		}
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage(errors.get(0).split("\\*", 2)[0]);
		error.setMsgKey(errors.get(0).split("\\*", 2)[1]);

		return error;
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public @ResponseBody ErrorResponseDto handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {

		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("Method does not supported");
		error.setMsgKey("please check method type");
		return error;
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponseDto handleAccessDeniedException(final AccessDeniedException ex) {
		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setMessage("Access denied");
		errorResponseDto.setMsgKey("Access denied");

		return errorResponseDto;

	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponseDto noHandlerFoundException(final NoHandlerFoundException exception) {

		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("URL not Found, Please check URL");
		error.setMsgKey("URLNotFound");
		return error;

	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponseDto handledMaxUploadSizeExceededException(
			final MaxUploadSizeExceededException exceededException) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("Please decrease your file size");
		error.setMsgKey("File size should be below 20MB");
		return error;

	}

	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorResponseDto handleJwtExpiredException(final ExpiredJwtException jwtExpired) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("JWT expired");
		error.setMsgKey("jwtExpired");
		return error;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorResponseDto handleJsonErrors(HttpMessageNotReadableException exception) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("Please enter valid information");
		error.setMsgKey("Enter valid input ");
		return error;
	}

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto numberFormatExceptionHandler(NumberFormatException exception) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("Invalid  input");
		error.setMsgKey("Enter valid input");
		return error;
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("Invalid uri");
		error.setMsgKey("Enter valid uri");
		return error;

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final Exception exception, HttpServletRequest request)
			throws IOException {
		ErrorLoggerEntity errorRequest = new ErrorLoggerEntity();
		errorRequest.setHost(InetAddress.getLoopbackAddress().getHostAddress());
		errorRequest.setMessage(exception.getMessage());
		errorRequest.setMethod(Enum.valueOf(MethodEnum.class, request.getMethod()));

		errorRequest.setUrl(request.getRequestURI());

		errorLoggerRepository.save(errorRequest);

		ErrorResponseDto error = new ErrorResponseDto();
		error.setMessage("Something went wrong. Please contact the administrator  " + exception.getMessage());
		error.setMsgKey("something went wrong.");
		return error;

	}
}
