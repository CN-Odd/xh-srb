package com.xh.srb.core.enums;

import com.xh.common.exception.Assert;
import com.xh.common.result.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BorrowerStatusEnum {
    NO_AUTH(0, "未认证"),
    AUTHING(1, "认证中"),
    AUTP_PASS(2, "认证通过"),
    AUTH_FAIL(-1, "认证失败");
    private Integer code;
    private String name;

    public static String getNameByStatus(Integer status) {
        BorrowerStatusEnum[] values = BorrowerStatusEnum.values();
        for (BorrowerStatusEnum borrowerStatusEnum : values) {
            if (borrowerStatusEnum.code.equals(status)) {
                return borrowerStatusEnum.getName();
            }
        }
        Assert.isNull(null, ResponseEnum.BORROWER_STATUS_ERROR);
        return null;
    }
}
