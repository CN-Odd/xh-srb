package com.xh.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private Integer code;
    private String msg;
    private Map<String, Object> data = new HashMap<>();

    private R(ResponseEum responseEum) {
        this.code = responseEum.getCode();
        this.msg = responseEum.getMsg();
    }

    public static R ok() {
        R r = new R(ResponseEum.SUCCESS);
        return r;
    }

    public static R error() {
        R r = new R(ResponseEum.ERROR);
        return r;
    }

    public static R setResult(ResponseEum responseEum) {
        return new R(responseEum);
    }

    public R setResultData(String key, Object val) {
        this.data.put(key, val);
        return this;
    }

    public R massage(String msg) {
        this.msg = msg;
        return this;
    }
}
