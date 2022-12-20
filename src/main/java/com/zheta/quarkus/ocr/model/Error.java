package com.zheta.quarkus.ocr.model;

import io.quarkus.runtime.annotations.RegisterForReflection;


@RegisterForReflection
public class Error {
	private int code = 500;
	private String exception;
	private String message;
	
	
	public Error() {
		// NOOP
	}
	
	public Error(Exception e) {
		this.exception = e.getClass().getSimpleName();
		this.message = e.getLocalizedMessage();
	}
	
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getException() {
		return exception;
	}
	
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
