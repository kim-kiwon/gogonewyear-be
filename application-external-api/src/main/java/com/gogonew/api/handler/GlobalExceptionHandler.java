package com.gogonew.api.handler;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gogonew.api.core.exception.CustomException;
import com.gogonew.api.core.response.ApiResult;
import com.gogonew.api.core.response.ResponseType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	// 미처리 예외
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResult> handleGlobalException(Exception ex) {
		ResponseType responseType = ResponseType.NOT_HANDLED;
		log.error(ex.getMessage(), ex);

		return ResponseEntity.status(responseType.getHttpStatus())
			.body(ApiResult.fail(responseType, ex.getMessage()));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResult<?>> handleCustomException(CustomException ex) {
		ResponseType responseType = ex.getResponseType();
		log.error(ex.getMessage(), ex);

		return ResponseEntity.status(responseType.getHttpStatus())
			.body(ApiResult.fail(responseType, ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ResponseType responseType = ResponseType.INVALID_INPUT;
		log.warn(ex.getMessage(), ex);

		return ResponseEntity.status(responseType.getHttpStatus())
			.body(ApiResult.fail(responseType, convertBindingErrorToString(ex.getBindingResult().getFieldErrors())));
	}

	private String convertBindingErrorToString(List<FieldError> errorList) {
		StringBuilder sb = new StringBuilder();
		for (FieldError error : errorList) {
			sb.append(error.getField());
			sb.append(" : ");
			sb.append(error.getDefaultMessage());
			sb.append(" \n ");
		}
		return sb.toString();
	}
}
