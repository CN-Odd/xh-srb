package com.xh.srb.sms;

import com.xh.srb.sms.util.SmsProperties;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceSmsApplicationTest {


    @Test
    public void testProperties() {
        System.out.println(SmsProperties.KEY_ID);
        System.out.println(SmsProperties.SIGN_NAME);

        byte[] bytes = RandomUtils.nextBytes(4);
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
        System.out.println();
        System.out.println(RandomUtils.nextInt(0, 9));
        System.out.println(RandomUtils.nextInt(0, 9));
        System.out.println(RandomUtils.nextInt(0, 9));
    }
}