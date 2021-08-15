package com.xh.srb.sms.service.impl;

import com.xh.common.exception.BusinessException;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    static public String SMS_PATH = "d:\\log\\sms\\sms.log";

    @Override
    public void send(String mobile, String templateCode, String code) {
        String smsMsg = templateCode.replace("%%%%", code);
        File smsFile = new File(SMS_PATH);
        try {
            if (!smsFile.exists()) {
                smsFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(smsFile, true);
            fileWriter.write(mobile + ":" + smsMsg);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            log.info("短信文件出现错误", e);
            throw new BusinessException(ResponseEnum.SEND_SMS_ERROR);
        }
    }
}
