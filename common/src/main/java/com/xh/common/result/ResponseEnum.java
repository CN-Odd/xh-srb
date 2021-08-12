package com.xh.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ResponseEnum {
    SUCCESS(0, "成功"),
    ERROR(-1, "服务器内部错误"),
    EXPORT_DATA_ERROR(104, "数据导出失败"),
    SERVLET_ERROR(-102, "servlet请求异常"),


    BAD_SQL_Grammar(-201, "SQL语法异常"),
    BORROW_AMOUNT_NULL_ERROR(-201, "借款额度不能为空"),
    MOBILE_NULL_ERROR(-202, "手机号不能为空"),
    PASSWORD_NULL_ERROR(-202, "密码不能为空"),
    CODE_NULL_ERROR(-202, "验证码不能为空"),
    MOBELE_ERROR(-203, "手机号码格式错误"),
    SEND_SMS_ERROR(-203, "短信发送出现问题"),
    CODE_ERROR(-203, "验证码错误"),
    MOBILE_EXIST_ERROR(-207, "手机已经注册"),
    LOGIN_MOBILE_ERROR(-208, "用户不存在"),
    LOGIN_PASSWORD_ERROR(-209, "密码错误"),
    LOGIN_AUTH_ERROR(-211, "未登录"),

    UPLOAD_ERROR(-301, "文件上传错误"),

    SMS_LIMIT_CONTROL_ERROR(-501, "短信获取过于频繁");


    private Integer code;
    private String msg;
}
