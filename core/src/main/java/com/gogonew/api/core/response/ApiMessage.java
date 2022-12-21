package com.gogonew.api.core.response;

import com.gogonew.api.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiMessage<T> {
    private boolean success;
    private T data;
    private ErrorCode errorCode;

    public static <T> ApiMessage<T> success(T data) {
        return ApiMessage.<T>builder()
            .success(true)
            .data(data)
            .errorCode(null)
            .build();
    }

    public static ApiMessage fail(ErrorCode errorCode) {
        return ApiMessage.builder()
            .success(false)
            .data(null)
            .errorCode(errorCode)
            .build();
    }
}
