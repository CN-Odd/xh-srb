package com.xh.srb.core.controller.api;

import com.alibaba.fastjson.JSON;
import com.xh.common.exception.Assert;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.base.util.JwtUtils;
import com.xh.srb.core.hfb.RequestHelper;
import com.xh.srb.core.pojo.vo.UserBindVO;
import com.xh.srb.core.service.UserBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@Api(tags = "用户绑定接口")
@RestController
@RequestMapping("/api/core/userBind")
@Slf4j
//@CrossOrigin
public class UserBindController {

    @Autowired
    private UserBindService userBindService;

    @ApiOperation("账户绑定提交数据")
    @PostMapping("/auth/bind")
    public R bind(@RequestBody UserBindVO userBindVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean checkToken = JwtUtils.checkToken(token);
        Assert.notTrue(checkToken, ResponseEnum.LOGIN_AUTH_ERROR);
        Long userId = JwtUtils.getUserId(token);
        String formStr = userBindService.commitBind(userId, userBindVO);
        return R.ok().setResultData("formStr", formStr);
    }


    @ApiOperation("hfb绑定后异步回调，更新绑定信息")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> switchMap = RequestHelper.switchMap(parameterMap);
        log.info("用户绑定异步回调：" + JSON.toJSONString(parameterMap));
        log.info("用户绑定异步回调：" + JSON.toJSONString(switchMap));
        if (!RequestHelper.isSignEquals(switchMap)) {
            log.info("用户绑定异步调用签名错误：" + JSON.toJSONString(switchMap));
            return "fail";
        }
        // 修改绑定状态
        userBindService.notify(switchMap);
        return  "success";
    }

//    @GetMapping("/bindInfo")
//    public R bindInfo(HttpServletRequest request) {
//        String token = request.getHeader("token");
//        boolean checkToken = JwtUtils.checkToken(token);
//        Assert.notTrue(checkToken, ResponseEnum.LOGIN_AUTH_ERROR);
//        Long userId = JwtUtils.getUserId(token);
//        return R.ok().setResultData("userBind");
//    }



    @PostMapping("/test")
    public R test(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> strings = parameterMap.keySet();
        for (String s : strings) {
            System.out.println(s);
        }
        return R.ok().message("test");
    }

}
