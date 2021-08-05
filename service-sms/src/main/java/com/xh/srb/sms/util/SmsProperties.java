package com.xh.srb.sms.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties implements InitializingBean {

    private String regionId;
    private String keyId;
    private String keySecret;
    private String templateCode;
    private String signName;

    static public String REDION_ID;
    static public String KEY_ID;
    static public String KEY_SECRET;
    static public String TEMPLATE_CODE;
    static public String SIGN_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        REDION_ID = regionId;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        TEMPLATE_CODE = templateCode;
        SIGN_NAME = signName;
    }
}
