package com.gogonew.api.core.response;

import com.gogonew.api.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private boolean isSuccess;
    private T data;
    private ErrorCode errorCode;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .isSuccess(true)
            .data(data)
            .errorCode(null)
            .build();
    }

    public static ApiResponse fail(ErrorCode errorCode) {
        return ApiResponse.builder()
            .isSuccess(false)
            .data(null)
            .errorCode(errorCode)
            .build();
    }
}
