package com.xh.common.exception;

import com.xh.common.result.ResponseEnum;
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

    public BusinessException(ResponseEnum responseEnum) {
        this.errorCode = responseEnum.getCode();
        this.message = responseEnum.getMsg();
    }

    public BusinessException(ResponseEnum uploadError, IOException e) {
        super(e);
        this.errorCode = uploadError.getCode();
        this.message = uploadError.getMsg();
    }
}
