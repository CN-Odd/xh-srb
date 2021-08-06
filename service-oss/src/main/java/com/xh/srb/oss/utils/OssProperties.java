package com.xh.srb.oss.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties implements InitializingBean {
    private String accessKeyID;
    private String accessKeySecret;
    private String bucketName;
    private String endpoint;

    static public String ACCESS_KEY_ID;
    static public String ACCESS_KEY_SECRET;
    static public String BUCKET_NAME;
    static public String END_POINT;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = accessKeyID;
        ACCESS_KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;
        END_POINT = endpoint;
    }
}
