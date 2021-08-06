package com.xh.common.exception;

import com.xh.common.result.ResponseEum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private Integer errorCode;
    private String message;

    public BusinessException(ResponseEum responseEum) {
        this.errorCode = responseEum.getCode();
        this.message = responseEum.getMsg();
    }

    public BusinessException(ResponseEum uploadError, IOException e) {
        super(e);
        this.errorCode = uploadError.getCode();
        this.message = uploadError.getMsg();
    }
}
