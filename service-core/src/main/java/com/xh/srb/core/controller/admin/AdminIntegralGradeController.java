package com.xh.srb.core.controller.admin;


import com.xh.common.exception.Assert;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.core.pojo.entity.IntegralGrade;
import com.xh.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Slf4j
@Api(tags = "积分等级管理")
@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Autowired
    private IntegralGradeService integralGradeService;

    @ApiOperation("积分等级列表")
    @GetMapping("/list")
    public R listAll() {
        log.info("hi i'm helen");
        log.warn("warning!!!");
        log.error("it's a error");
        List<IntegralGrade> integralGrades = integralGradeService.list();
        R ok = R.ok();
        return ok.setResultData("list", integralGrades);
    }

    @ApiOperation(value = "根据id删除积分等级", notes = "逻辑删除数据")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(value = "数据id", required = true, example = "1")
            @PathVariable Long id) {
        boolean result = integralGradeService.removeById(id);

        return result ? R.ok().message("删除成功") : R.error().message("删除失败");
    }

    @ApiOperation(value = "新增积分等级")
    @PostMapping("/save")
    public R save(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade) {
        Assert.isNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        boolean saved = integralGradeService.save(integralGrade);
        return saved ? R.ok().message("新增积分等级成功！") : R.error().message("新增积分等级失败！");
    }

    @ApiOperation(value = "根据id获取积分等级")
    @GetMapping("/get/{id}")
    public R getById(
            @ApiParam(value = "数据id")
            @PathVariable(name = "id") Long id) throws Exception {
        IntegralGrade integralGrade = integralGradeService.getById(id);
        return null != integralGrade ? R.ok().setResultData("record", integralGrade) : R.error().message("数据不存在");
    }


    @ApiOperation(value = "更新积分等级")
    @PutMapping("/update")
    public R update(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade) {
        boolean result = integralGradeService.updateById(integralGrade);
        return result ? R.ok().message("更新积分等级成功") : R.error().message("更新积分等级失败");
    }
}

