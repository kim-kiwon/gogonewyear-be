package com.gogonew.api.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    SERVER_ERROR(500, "C000", "서버 오류가 발생하였습니다."),
    INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 입력입니다."),
    NO_DATA(400, "C002", "해당 데이터가 존재하지 않습니다.");

    private int status;
    private final String code;
    private final String message;
}
