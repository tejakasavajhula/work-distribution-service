package com.workdistribution.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class WDSExceptionHandler {

	@ExceptionHandler(ApiBusinessException.class)
	public ResponseEntity<String> handleBusinessException(ApiBusinessException exception) {
		return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());		
	}
}
