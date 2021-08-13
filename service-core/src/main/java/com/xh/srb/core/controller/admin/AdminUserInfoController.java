package com.xh.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.common.result.R;
import com.xh.srb.core.pojo.entity.UserInfo;
import com.xh.srb.core.pojo.query.UserInfoQuery;
import com.xh.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public R listPage(@PathVariable("index") Integer index,
                      @PathVariable("size") Integer size,
                      UserInfoQuery userInfoQuery) {
        index = index == 0 ? 1 : index;
        size = size == 0 ? 10 : size;
        Page<UserInfo> pageParam = new Page<>(index, size);
        IPage<UserInfo> pageModel = userInfoService.listPage(pageParam, userInfoQuery);

        return R.ok().setResultData("pageModel", pageModel);
    }

}
