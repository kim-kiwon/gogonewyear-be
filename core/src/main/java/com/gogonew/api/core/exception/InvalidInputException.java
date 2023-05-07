package com.gogonew.api.core.exception;

import com.gogonew.api.core.response.ResponseType;

public class InvalidInputException extends CustomException {
	public InvalidInputException(String message) {
		super(message);
	}

	@Override
	public ResponseType getResponseType() {
		return ResponseType.INVALID_INPUT;
	}
}
