package com.xh.srb.core.controller.admin;

import com.xh.common.result.R;
import com.xh.srb.core.pojo.entity.UserLoginRecord;
import com.xh.srb.core.service.UserLoginRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("用户登录记录接口")
@Slf4j
@RestController
@RequestMapping("/admin/core/userLoginRecord")
@CrossOrigin
public class AdminUserLoginRecoderController {

    @Autowired
    private UserLoginRecordService userLoginRecordService;

    @ApiOperation(value = "获取前50登录日志列表")
    @GetMapping("/listTop50/{userId}")
    public R listTop50(
            @ApiParam("用户ID")
            @PathVariable("userId") Long userId) {
        List<UserLoginRecord> recordList = userLoginRecordService.listTop50(userId);
        return R.ok();
    }
}
