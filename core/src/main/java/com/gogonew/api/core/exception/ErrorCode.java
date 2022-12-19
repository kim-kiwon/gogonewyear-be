package com.gogonew.api.core.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SERVER_ERROR(500, "C000", "서버 오류가 발생하였습니다."),
    INVALID_INPUT_VALUE(400, "C001", "비정상적인 입력입니다.");

    private int status;
    private final String code;
    private final String message;


    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
