package com.gogonew.api.core.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResult<T> {
    private Boolean success;
    private String businessCode;
    private T result;
    private String errorMsg;

    public static <T> ApiResult<T> success(T result) {
        return ApiResult.<T>builder()
            .success(true)
            .businessCode(ResponseType.SUCCESS.getBusinessCode())
            .result(result)
            .errorMsg(null)
            .build();
    }

    public static ApiResult fail(ResponseType responseType, String errorMsg) {
        return ApiResult.builder()
            .success(false)
            .businessCode(responseType.getBusinessCode())
            .errorMsg(errorMsg)
            .build();
    }
}
