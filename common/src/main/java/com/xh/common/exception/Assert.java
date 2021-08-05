package com.xh.common.exception;

import com.xh.common.result.ResponseEum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Assert {

    public static void isNull(Object o, ResponseEum responseEum) {
        if (o != null) {
            log.info("obj is null ---------------");
            throw new BusinessException(responseEum);
        }
    }

    public static void notNull(Object o, ResponseEum responseEum) {
        if (o == null) {
            log.info("obj is null ---------------");
            throw new BusinessException(responseEum);
        }
    }

    public static void notTrue(Boolean b, ResponseEum responseEum) {
        if (!b) {
            throw new BusinessException(responseEum);
        }
    }
}
