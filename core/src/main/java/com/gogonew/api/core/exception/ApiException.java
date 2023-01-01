package com.gogonew.api.core.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    // 미처리 예외 전환용 생성자
    public ApiException(Exception ex) {
        super(ex);
        this.errorCode = ErrorCode.SERVER_ERROR;
    }

    public ApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
