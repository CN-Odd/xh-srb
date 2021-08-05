package com.xh.srb.sms.controller.api;

import com.xh.common.exception.Assert;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEum;
import com.xh.common.utils.RandomUtils;
import com.xh.common.utils.RegexValidateUtils;
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

    @ApiOperation("获取验证码")
    @GetMapping("send/{mobile}")
    public R send(
            @ApiParam("手机号")
            @PathVariable(value = "mobile") String mobile) {
        Assert.notNull(mobile, ResponseEum.MOBILE_NULL_ERROR);
        Assert.notTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEum.MOBELE_ERROR);
        String key = "srb:sms:code:" + mobile;
        String code = redisTemplate.opsForValue().get(key);
        Assert.isNull(code, ResponseEum.SMS_LIMIT_CONTROL_ERROR);
        code = RandomUtils.getFourBitRandom();
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, code);
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        return R.ok().massage("发送短信成功");
    }
}
