package com.xh.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.common.result.R;
import com.xh.srb.core.pojo.entity.Borrower;
import com.xh.srb.core.pojo.entity.UserInfo;
import com.xh.srb.core.pojo.vo.BorrowerApprovalVO;
import com.xh.srb.core.pojo.vo.BorrowerDetailVO;
import com.xh.srb.core.service.BorrowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("借款信息管理")
@RestController
@Slf4j
@RequestMapping("/admin/core/borrower")
public class AdminBorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @ApiOperation("借款信息列表")
    @GetMapping("/list/{index}/{size}")
    public R listPage(
            @PathVariable("index") Integer index,
            @PathVariable("size") Integer size,
            @RequestParam("keyword") String keyword) {
        index = index == 0 ? 1 : index;
        size = size == 0 ? 10 : size;
        Page<Borrower> page = new Page<>(index, size);
        IPage<Borrower> pageModel = borrowerService.listPage(page, keyword);
        return R.ok().setResultData("pageModel", pageModel);
    }

    @ApiOperation("查询借款信息")
    @GetMapping("/show/{id}")
    public R detail(@PathVariable("id") Integer id) {
        BorrowerDetailVO borrowerDetailVO = borrowerService.showBorrowerDetail(id);
        return R.ok().setResultData("borrowerDetailVO", borrowerDetailVO);
    }

    @ApiOperation("借款信息审核")
    @PostMapping("/approval")
    public R approval(@RequestBody BorrowerApprovalVO borrowerApprovalVO) {
        borrowerService.approval(borrowerApprovalVO);
        return R.ok();
    }
}
