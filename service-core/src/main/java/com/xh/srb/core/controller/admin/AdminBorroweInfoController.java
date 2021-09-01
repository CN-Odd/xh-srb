package com.xh.srb.core.controller.admin;

import com.xh.common.result.R;
import com.xh.srb.core.pojo.entity.BorrowInfo;
import com.xh.srb.core.service.BorrowInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("借款人信息管理")
@RestController
@RequestMapping("/admin/core/borrowInfo")
public class AdminBorroweInfoController {

    @Autowired
    private BorrowInfoService borrowInfoService;
    @GetMapping("/list")
    public R list() {
        List<BorrowInfo> borrowInfoList = borrowInfoService.selectList();
    }
}
