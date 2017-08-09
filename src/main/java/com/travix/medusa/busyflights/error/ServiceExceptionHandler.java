package com.travix.medusa.busyflights.error;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Error Handler Class
 */
@EnableWebMvc
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, String> responseBody = new HashMap<String, String>();
		responseBody.put("path", request.getContextPath());
		responseBody.put("message", "The URL you have reached is not in service at this time (404).");
		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_FOUND);
	}
}
