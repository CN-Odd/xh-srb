package com.xh.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum IntegralEnums {
    BORROWER_IDCARD(30, "身份证信息通过"),
    BORROWER_HOUSE(100, "房产信息通过"),
    BORROWER_CAR(60, "车辆信息通过");
    private Integer integral;
    private String msg;
}
