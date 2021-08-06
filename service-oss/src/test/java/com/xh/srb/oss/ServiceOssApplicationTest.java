package com.xh.srb.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.xh.srb.oss.utils.OssProperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceOssApplicationTest {

    @Test
    public void testAliyunOss() {
        String objectName = "avatar/db-2.png";
        OSS ossClient = new OSSClientBuilder().build(OssProperties.END_POINT, OssProperties.ACCESS_KEY_ID, OssProperties.ACCESS_KEY_SECRET);

        // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
        // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
        ossClient.getObject(new GetObjectRequest(OssProperties.BUCKET_NAME, objectName), new File("D:\\localpath\\examplefile.png"));

        // 关闭OSSClient。
        ossClient.shutdown();

    }
}
