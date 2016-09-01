package com.studio4365.spring.healthcheck.exception;

import java.util.Map;

public class HealthCheckException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> response;

	public HealthCheckException(Map<String, Object> response) {
		this.response = response;
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}
	
}
