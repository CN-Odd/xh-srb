package com.xh.srb.core.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum UserBindEnum {
    NO_BIND(0, "未绑定"),
    BINK_OK(1, "绑定成功"),
    BIND_FAIL(-1, "绑定失败")

    ;
    private Integer code;
    private String msg;
}
