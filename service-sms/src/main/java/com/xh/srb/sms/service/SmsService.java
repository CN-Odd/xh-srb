package com.xh.srb.sms.service;

public interface SmsService {
    void send(String mobile, String templateCode, String code);
}
