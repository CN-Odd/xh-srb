package com.xh.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.common.exception.Assert;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.core.enums.BorrowInfoStatusEnum;
import com.xh.srb.core.enums.BorrowerStatusEnum;
import com.xh.srb.core.enums.UserBindEnum;
import com.xh.srb.core.mapper.IntegralGradeMapper;
import com.xh.srb.core.mapper.UserInfoMapper;
import com.xh.srb.core.pojo.entity.BorrowInfo;
import com.xh.srb.core.mapper.BorrowInfoMapper;
import com.xh.srb.core.pojo.entity.IntegralGrade;
import com.xh.srb.core.pojo.entity.UserInfo;
import com.xh.srb.core.pojo.vo.BorrowInfoVO;
import com.xh.srb.core.service.BorrowInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.srb.core.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Service
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements BorrowInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private IntegralGradeMapper integralGradeMapper;
    @Autowired
    private DictService dictService;

    @Override
    public BigDecimal getBorrowAmount(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Integer integral = userInfo.getIntegral();

        QueryWrapper<IntegralGrade> integralGradeQueryWrapper = new QueryWrapper<>();
        integralGradeQueryWrapper
                .ge("integral_end", integral)
                .le("integral_start", integral);
        IntegralGrade integralGrade = integralGradeMapper.selectOne(integralGradeQueryWrapper);
        if(integralGrade == null){
            return new BigDecimal("0");
        }
        return integralGrade.getBorrowAmount();
    }

    @Override
    public void saveBorrowInfoByUserId(BorrowInfoVO borrowInfoVO, Long userId) {
        //获取userInfo的用户数据
        UserInfo userInfo = userInfoMapper.selectById(userId);

        //判断用户绑定状态
        Assert.notTrue(
                userInfo.getBindStatus().intValue() == UserBindEnum.BINK_OK.getCode().intValue(),
                ResponseEnum.USER_NO_BIND_ERROR);

        //判断用户信息是否审批通过
        Assert.notTrue(
                userInfo.getBorrowAuthStatus().intValue() == BorrowerStatusEnum.AUTP_PASS.getCode().intValue(),
                ResponseEnum.USER_NO_AMOUNT_ERROR);


        BigDecimal borrowAmount = this.getBorrowAmount(userId);
        Assert.notTrue(borrowAmount.compareTo(borrowInfoVO.getAmount()) >= 0, ResponseEnum.OUT_OF_AMOUNT);
        BorrowInfo borrowInfo = new BorrowInfo();
        BeanUtils.copyProperties(borrowInfoVO, borrowInfo);

        //存储数据
        borrowInfo.setUserId(userId);
        //百分比转成小数
        borrowInfo.setBorrowYearRate( borrowInfoVO.getBorrowYearRate().divide(new BigDecimal(100)));
        borrowInfo.setStatus(BorrowInfoStatusEnum.CHECK_RUN.getCode());
        baseMapper.insert(borrowInfo);

    }

    @Override
    public Integer getBorrowInfoStatus(Long userId) {
        QueryWrapper<BorrowInfo> borrowInfoQueryWrapper = new QueryWrapper<>();
        borrowInfoQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowInfoQueryWrapper);

        if(objects.size() == 0){
            //借款人尚未提交信息
            return BorrowInfoStatusEnum.NO_AUTH.getCode();
        }
        Integer status = (Integer)objects.get(0);
        return status;
    }

    @Override
    public List<BorrowInfo> selectList() {
        List<BorrowInfo> borrowInfos = baseMapper.selectList(null);
        borrowInfos.forEach(borrowInfo -> {
            String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", borrowInfo.getReturnMethod());
            String moneyUse = dictService.getNameByParentDictCodeAndValue("moneyUse", borrowInfo.getMoneyUse());
            String status = BorrowInfoStatusEnum.getMsgByStatus(borrowInfo.getStatus());

        });
        return null;
    }
}
