package com.gogonew.api.handler;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.core.response.ApiMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // 미처리 에러. 클라이언트에게 서버에러로 전송 및 로깅.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiMessage> handleGlobalException(Exception ex) {
        log.error("[Global] 미처리 예외 발생.", ex);
        return getErrorResponse(ErrorCode.SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiMessage> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("[Validation] 검증 예외 발생.", ex);
        return getErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiMessage> handleApiException(ApiException ex) {
        log.error(ex.getMessage(), ex);
        return getErrorResponse(ex.getErrorCode());
    }

    private ResponseEntity<ApiMessage> getErrorResponse(ErrorCode errorCode) {
        return ResponseEntity
            .status(errorCode.getStatus())
            .body(ApiMessage.fail(errorCode));
    }
}
