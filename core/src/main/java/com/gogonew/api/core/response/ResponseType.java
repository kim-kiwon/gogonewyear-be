package com.gogonew.api.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {
	SUCCESS(200, "1000"),
	INVALID_INPUT(400, "4001"),
	NOT_FOUND(404, "4002"),
	NOT_HANDLED(500, "9999"),
	;

	private int httpStatus;
	private String businessCode;
}
