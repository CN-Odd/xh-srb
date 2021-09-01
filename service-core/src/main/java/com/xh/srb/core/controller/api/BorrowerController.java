package com.xh.srb.core.controller.api;


import com.xh.common.exception.Assert;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.base.util.JwtUtils;
import com.xh.srb.core.pojo.vo.BorrowerVO;
import com.xh.srb.core.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@RestController
@RequestMapping("/api/core/borrower")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @PostMapping("/auth/save")
    public R save(@RequestBody BorrowerVO borrowerVO, HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean checkToken = JwtUtils.checkToken(token);
        Assert.notTrue(checkToken, ResponseEnum.LOGIN_AUTH_ERROR);
        Long userId = JwtUtils.getUserId(token);

        borrowerService.saveBorrowerVOByUserId(borrowerVO, userId);
        return R.ok();
    }

    @GetMapping("/auth/getBorrowerStatus")
    public R getBorrowerStatus(HttpServletRequest request) {
        String token = request.getHeader("token");
        boolean checkToken = JwtUtils.checkToken(token);
        Assert.notTrue(checkToken, ResponseEnum.LOGIN_AUTH_ERROR);
        Long userId = JwtUtils.getUserId(token);
        Integer borrowerStatus = borrowerService.getBorrowerStatusByUserId(userId);
        return R.ok().setResultData("borrowerStatus", borrowerStatus);
    }

}

