package com.gogonew.api.core.exception;

import com.gogonew.api.core.response.ResponseType;

public class NoDataException extends CustomException {
	public NoDataException(String message) {
		super(message);
	}

	@Override
	public ResponseType getResponseType() {
		return ResponseType.NOT_FOUND;
	}
}