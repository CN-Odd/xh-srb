package com.xh.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private Integer code;
    private String msg;
    private Map<String, Object> data = new HashMap<>();

    private R(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
    }

    public static R ok() {
        R r = new R(ResponseEnum.SUCCESS);
        return r;
    }

    public static R error() {
        R r = new R(ResponseEnum.ERROR);
        return r;
    }

    public static R setResult(ResponseEnum responseEnum) {
        return new R(responseEnum);
    }

    public R setResultData(String key, Object val) {
        this.data.put(key, val);
        return this;
    }

    public R message(String msg) {
        this.msg = msg;
        return this;
    }
}
