package com.xh.srb.core.hfb;

public class HfbConst {
    /**
     * 用户绑定
     */
    //用户绑定汇付宝平台url地址
    public static final String USERBIND_URL = "http://localhost:9999/userBind/BindAgreeUserV2";
    //用户绑定异步回调
    public static final String USERBIND_NOTIFY_URL = "http://localhost/api/core/userBind/notify";
    //用户绑定同步回调
    public static final String USERBIND_RETURN_URL = "http://localhost:3000/user";
    public static final String AGENT_ID = "021" ;
    public static final String SIGN_KEY = "9876543210";
}
