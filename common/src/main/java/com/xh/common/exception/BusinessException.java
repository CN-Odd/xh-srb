package com.xh.common.exception;

import com.xh.common.result.ResponseEum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private Integer errorCode;
    private String message;

    public BusinessException(ResponseEum responseEum) {
        this.errorCode = responseEum.getCode();
        this.message = responseEum.getMsg();
    }
}
