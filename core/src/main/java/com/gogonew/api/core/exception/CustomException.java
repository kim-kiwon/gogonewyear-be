package com.gogonew.api.core.exception;

import com.gogonew.api.core.response.ResponseType;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException{

	public CustomException(String message) {
		super(message);
	}

	public abstract ResponseType getResponseType();
}
