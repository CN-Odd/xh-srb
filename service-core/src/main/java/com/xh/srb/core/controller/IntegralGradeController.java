package com.xh.srb.core.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.srb.core.mapper.IntegralGradeMapper;
import com.xh.srb.core.pojo.entity.IntegralGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@RestController
@RequestMapping("/integralGrade")
public class IntegralGradeController {

    @Autowired
    IntegralGradeMapper integralGradeMapper;

    @GetMapping
    public Object test() {
        QueryWrapper<IntegralGrade> queryWrapper = new QueryWrapper<>();

        return integralGradeMapper.selectList(null);
    }
}

