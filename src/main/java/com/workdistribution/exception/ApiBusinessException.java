package com.workdistribution.exception;

import org.springframework.http.HttpStatus;

public class ApiBusinessException extends RuntimeException {

	private final HttpStatus status;
	
	private static final long serialVersionUID = 1L;

	public ApiBusinessException(String message,HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

	public HttpStatus getStatus() {
		return status;
	}
}
