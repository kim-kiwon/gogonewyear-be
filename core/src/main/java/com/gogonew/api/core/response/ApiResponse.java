package com.gogonew.api.core.response;

import com.gogonew.api.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
            .build();
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
            .isSuccess(false)
            .errorCode(errorCode)
            .build();
    }
}
