package com.explore.android.core.util;

public class ExParseException extends RuntimeException {
	private static final long serialVersionUID = -6700259740284109210L;
	public String response;

	ExParseException(String response) {
		this.response = response;
	}
}
