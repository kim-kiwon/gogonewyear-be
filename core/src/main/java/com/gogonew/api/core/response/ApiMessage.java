package com.gogonew.api.core.response;

import com.gogonew.api.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiMessage<T> {
    private int status;
    private T data;
    private String errorMsg;

    public static <T> ApiMessage<T> success(T data) {
        return ApiMessage.<T>builder()
            .status(200)
            .data(data)
            .errorMsg(null)
            .build();
    }

    public static ApiMessage fail(ErrorCode errorCode) {
        return ApiMessage.builder()
            .status(errorCode.getStatus())
            .data(null)
            .errorMsg(errorCode.getMessage())
            .build();
    }
}
