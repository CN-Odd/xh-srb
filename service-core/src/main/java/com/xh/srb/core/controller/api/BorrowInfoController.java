package com.xh.srb.core.controller.api;


import com.xh.common.result.R;
import com.xh.srb.base.util.JwtUtils;
import com.xh.srb.core.pojo.vo.BorrowInfoVO;
import com.xh.srb.core.service.BorrowInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 前端控制器
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Api(tags = "借款信息")
@RestController
@RequestMapping("/api/core/borrowInfo")
public class BorrowInfoController {

    @Autowired
    private BorrowInfoService borrowInfoService;

    @ApiOperation(value = "获取借款人额度")
    @GetMapping("/borrowAmount")
    public R borrowAmount(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        BigDecimal borrowAmount = borrowInfoService.getBorrowAmount(userId);
        return R.ok().setResultData("borrowAmount", borrowAmount);
    }

    @PostMapping("/auth/save")
    public R save(@RequestBody BorrowInfoVO borrowInfoVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowInfoService.saveBorrowInfoByUserId(borrowInfoVO, userId);
        return R.ok();
    }

    @GetMapping("/auth/borrowInfoStatus")
    public R borrowInfoStatus(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer borrowStatus = borrowInfoService.getBorrowInfoStatus(userId);
        return R.ok().setResultData("borrowInfoStatus", borrowStatus);
    }

}

