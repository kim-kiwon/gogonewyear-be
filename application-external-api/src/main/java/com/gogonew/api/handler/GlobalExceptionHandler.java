package com.gogonew.api.handler;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.core.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException apiEx) {
        return getResponseEntity(apiEx);
    }

    // 개발자가 의도하지 않은 미처리 에러
    // 클라이언트에게 서버에러로 전송 및 로깅
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(Exception ex) {
        ApiException apiEx = new ApiException(ex);

        return getResponseEntity(apiEx);
    }

    private ResponseEntity<ApiResponse> getResponseEntity(ApiException apiEx) {
        ErrorCode errorCode = apiEx.getErrorCode();

        return ResponseEntity
            .status(errorCode.getStatus())
            .body(ApiResponse.fail(errorCode));
    }
}
