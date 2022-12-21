package com.gogonew.api.core.response;

import com.gogonew.api.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorCode errorCode;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .success(true)
            .data(data)
            .errorCode(null)
            .build();
    }

    public static ApiResponse fail(ErrorCode errorCode) {
        return ApiResponse.builder()
            .success(false)
            .data(null)
            .errorCode(errorCode)
            .build();
    }
}
