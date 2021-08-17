package com.xh.srb.sms.controller.api;

import com.xh.common.exception.Assert;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEnum;
import com.xh.common.util.RandomUtils;
import com.xh.common.util.RegexValidateUtils;
import com.xh.srb.sms.client.CoreUserInfoClient;
import com.xh.srb.sms.service.SmsService;
import com.xh.srb.sms.util.SmsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Api(tags = "短信管理")
@CrossOrigin
@RestController
@RequestMapping("/api/sms")
public class ApiSmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CoreUserInfoClient coreUserInfoClient;

    @ApiOperation("获取验证码")
    @GetMapping("send/{mobile}")
    public R send(
            @ApiParam("手机号")
            @PathVariable(value = "mobile") String mobile) {
        Assert.isNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBELE_ERROR);
        Assert.notTrue(!coreUserInfoClient.checkMobile(mobile), ResponseEnum.MOBILE_EXIST_ERROR);
        String key = "srb:sms:code:" + mobile;
        String code = redisTemplate.opsForValue().get(key);
        Assert.notNull(code, ResponseEnum.SMS_LIMIT_CONTROL_ERROR);
        code = RandomUtils.getFourBitRandom();
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, code);
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        return R.ok().message("发送短信成功");
    }
}
