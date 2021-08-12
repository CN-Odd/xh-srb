package com.xh.common.exception;

import com.xh.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Assert {

    public static void notNull(Object o, ResponseEnum responseEnum) {
        if (o != null) {
            log.info("obj is null ---------------");
            throw new BusinessException(responseEnum);
        }
    }

    public static void isNull(Object o, ResponseEnum responseEnum) {
        if (o == null) {
            log.info("obj not null ---------------");
            throw new BusinessException(responseEnum);
        }
    }

    public static void notTrue(Boolean b, ResponseEnum responseEnum) {
        if (!b) {
            throw new BusinessException(responseEnum);
        }
    }
}
