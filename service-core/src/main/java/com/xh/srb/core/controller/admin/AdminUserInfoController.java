package com.xh.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.common.result.R;
import com.xh.srb.core.pojo.entity.UserInfo;
import com.xh.srb.core.pojo.query.UserInfoQuery;
import com.xh.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("会员管理")
@RestController
@RequestMapping("/admin/core/userInfo")
@Slf4j
@CrossOrigin
public class AdminUserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("会员信息查询")
    @PostMapping("/list/{index}/{size}")
    public R listPage(
            @ApiParam("页号")
            @PathVariable("index") Integer index,
            @PathVariable("size") Integer size,
            @ApiParam("查询条件") UserInfoQuery userInfoQuery) {
        index = index == 0 ? 1 : index;
        size = size == 0 ? 10 : size;
        Page<UserInfo> pageParam = new Page<>(index, size);
        IPage<UserInfo> pageModel = userInfoService.listPage(pageParam, userInfoQuery);

        return R.ok().setResultData("pageModel", pageModel);
    }

    @ApiOperation("会员锁定与解锁")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @ApiParam("用户号")
            @PathVariable("id") Long id,
            @ApiParam("状态 0-锁定， 1-解锁")
            @PathVariable("status") Integer status) {

        userInfoService.lock(id, status);
        return R.ok().message(status == 1 ? "解锁成功" : "锁定成功");
    }

}
