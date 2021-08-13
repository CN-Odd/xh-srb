package com.xh.srb.core.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xh.common.exception.Assert;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEnum;
import com.xh.common.util.RegexValidateUtils;
import com.xh.srb.base.util.JwtUtils;
import com.xh.srb.core.pojo.vo.LoginVO;
import com.xh.srb.core.pojo.vo.RegisterVO;
import com.xh.srb.core.pojo.vo.UserInfoVO;
import com.xh.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "会员接口")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/core/userInfo")
public class UserInfoController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVO registerVO) {
        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();

        Assert.isNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBELE_ERROR);
        Assert.isNull(password, ResponseEnum.PASSWORD_NULL_ERROR);
        Assert.isNull(code, ResponseEnum.CODE_NULL_ERROR);

        //校验验证码
        String codeGen = redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
        Assert.notTrue(codeGen.equals(code), ResponseEnum.CODE_ERROR);
        userInfoService.register(registerVO);

        return R.ok().massage("注册成功");
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        Assert.isNull(loginVO.getMobile(), ResponseEnum.MOBILE_NULL_ERROR);
        Assert.isNull(loginVO.getPassword(), ResponseEnum.PASSWORD_NULL_ERROR);

        UserInfoVO userInfoVO = userInfoService.login(loginVO, request.getRemoteAddr());

        return R.ok().massage("登录成功").setResultData("userInfo", userInfoVO);
    }

    @ApiOperation("令牌校验")
    @GetMapping("/checkout")
    public R checkOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean checkToken = JwtUtils.checkToken(token);
        Assert.notTrue(checkToken, ResponseEnum.LOGIN_AUTH_ERROR);
        return R.ok();
    }


}
