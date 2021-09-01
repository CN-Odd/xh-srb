package com.xh.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BorrowInfoStatusEnum {
    NO_AUTH(0, "未提交"),
    CHECK_RUN(1, "审核中"),
    CHECK_OK(2, "审核通过"),
    CHECK_FAIL(-1, "审核不通过"),
    ;
    private Integer code;
    private String msg;

    public static String getMsgByStatus(Integer status) {
        BorrowInfoStatusEnum[] values = BorrowInfoStatusEnum.values();
        for (BorrowInfoStatusEnum value : values) {
            if( value.getCode().equals(status)) {
                return value.getMsg();
            }
        }
        return null;
    }
}
