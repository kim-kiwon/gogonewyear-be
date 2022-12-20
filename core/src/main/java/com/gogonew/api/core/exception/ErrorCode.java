package com.gogonew.api.core.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;


@Getter
@JsonFormat(shape = Shape.OBJECT) // Json 응닶으로 Enum 값만이 아닌 객체 전체 반환하도록
public enum ErrorCode {
    SERVER_ERROR(500, "C000", "서버 오류가 발생하였습니다."),
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력입니다.");

    private int status;
    private final String code;
    private final String message;


    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
