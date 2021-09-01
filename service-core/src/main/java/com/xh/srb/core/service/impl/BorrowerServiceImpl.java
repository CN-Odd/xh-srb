package com.xh.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.common.exception.Assert;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.core.enums.BorrowerStatusEnum;
import com.xh.srb.core.enums.IntegralEnums;
import com.xh.srb.core.mapper.*;
import com.xh.srb.core.pojo.entity.*;
import com.xh.srb.core.pojo.vo.BorrowerApprovalVO;
import com.xh.srb.core.pojo.vo.BorrowerAttachVO;
import com.xh.srb.core.pojo.vo.BorrowerDetailVO;
import com.xh.srb.core.pojo.vo.BorrowerVO;
import com.xh.srb.core.service.BorrowerService;
import com.xh.srb.core.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserBindMapper userBindMapper;
    @Autowired
    private BorrowerAttachMapper borrowerAttachMapper;
    @Autowired
    private UserIntegralMapper userIntegralMapper;
    @Autowired
    private DictService dictService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        QueryWrapper<UserBind> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userInfo.getId());
        UserBind userBind = userBindMapper.selectOne(queryWrapper);

        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);

        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        borrower.setStatus(BorrowerStatusEnum.AUTHING.getCode());

        baseMapper.insert(borrower);

        borrowerVO.getBorrowerAttachList().forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });

        userInfo.setBorrowAuthStatus(borrower.getStatus());
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public Integer getBorrowerStatusByUserId(Long userId) {
        QueryWrapper<Borrower> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("status").eq("user_id", userId);
        List<Object> objs = baseMapper.selectObjs(queryWrapper);
        Integer status;
        status = objs.size() == 0 ? BorrowerStatusEnum.NO_AUTH.getCode() : (Integer) objs.get(0);
        return status;
    }

    @Override
    public IPage<Borrower> listPage(Page<Borrower> page, String keyword) {
        QueryWrapper<Borrower> queryWrapper = null;
        if (null != keyword) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name", keyword)
                    .or().like("id_card", keyword)
                    .or().like("mobile", keyword);
        }
        Page<Borrower> borrowerPage = baseMapper.selectPage(page, queryWrapper);
        return borrowerPage;
    }

    @Override
    public BorrowerDetailVO showBorrowerDetail(Integer id) {
        Borrower borrower = baseMapper.selectById(id);
        Assert.isNull(borrower, ResponseEnum.NOT_SUCH_BORROWER);

        BorrowerDetailVO borrowerDetailVO = new BorrowerDetailVO();
        BeanUtils.copyProperties(borrower, borrowerDetailVO);

        borrowerDetailVO.setSex(borrower.getSex() == 1 ? "男" : "女");
        borrowerDetailVO.setMarry(borrower.getMarry() ? "是" : "否");
        borrowerDetailVO.setStatus(BorrowerStatusEnum.getNameByStatus(borrower.getStatus()));

        //计算下拉列表选中内容
        String education = dictService.getNameByParentDictCodeAndValue("education", borrower.getEducation());
        String industry = dictService.getNameByParentDictCodeAndValue("moneyUse", borrower.getIndustry());
        String income = dictService.getNameByParentDictCodeAndValue("income", borrower.getIncome());
        String returnSource = dictService.getNameByParentDictCodeAndValue("returnSource", borrower.getReturnSource());
        String contactsRelation = dictService.getNameByParentDictCodeAndValue("relation", borrower.getContactsRelation());
        borrowerDetailVO.setEducation(education);
        borrowerDetailVO.setIndustry(industry);
        borrowerDetailVO.setIncome(income);
        borrowerDetailVO.setReturnSource(returnSource);
        borrowerDetailVO.setContactsRelation(contactsRelation);

        QueryWrapper<BorrowerAttach> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("borrower_id", borrower.getId());
        List<BorrowerAttach> borrowerAttaches = borrowerAttachMapper.selectList(queryWrapper);
        List<BorrowerAttachVO> borrowerAttachVOS = new ArrayList<>(borrowerAttaches.size());
        borrowerAttaches.forEach(borrowerAttach -> {
            BorrowerAttachVO borrowerAttachVO = new BorrowerAttachVO();
            BeanUtils.copyProperties(borrowerAttach, borrowerAttachVO);
            borrowerAttachVOS.add(borrowerAttachVO);
        });
        borrowerDetailVO.setBorrowerAttachVOList(borrowerAttachVOS);
        return borrowerDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void approval(BorrowerApprovalVO borrowerApprovalVO) {
        Long borrowerId = borrowerApprovalVO.getBorrowerId();
        Borrower borrower = baseMapper.selectById(borrowerId);
        Assert.isNull(borrower, ResponseEnum.NOT_SUCH_BORROWER);
        borrower.setStatus(borrowerApprovalVO.getStatus());
        baseMapper.updateById(borrower);

        UserInfo userInfo = userInfoMapper.selectById(borrower.getUserId());
        Assert.isNull(userInfo, ResponseEnum.USER_NOT_FOUND);

        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(userInfo.getId());
        userIntegral.setContent("借款人基本信息");
        userIntegral.setIntegral(borrowerApprovalVO.getInfoIntegral());
        userIntegralMapper.insert(userIntegral);

        Integer currentIntegral = userIntegral.getIntegral();
        if (borrowerApprovalVO.getIsIdCardOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setIntegral(IntegralEnums.BORROWER_IDCARD.getIntegral());
            userIntegral.setContent(IntegralEnums.BORROWER_IDCARD.getMsg());
            currentIntegral += userIntegral.getIntegral();
            userIntegralMapper.insert(userIntegral);
        }
        if (borrowerApprovalVO.getIsHouseOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setIntegral(IntegralEnums.BORROWER_HOUSE.getIntegral());
            userIntegral.setContent(IntegralEnums.BORROWER_HOUSE.getMsg());
            currentIntegral += userIntegral.getIntegral();
            userIntegralMapper.insert(userIntegral);
        }
        if (borrowerApprovalVO.getIsCarOk()) {
            userIntegral = new UserIntegral();
            userIntegral.setIntegral(IntegralEnums.BORROWER_CAR.getIntegral());
            userIntegral.setContent(IntegralEnums.BORROWER_CAR.getMsg());
            currentIntegral += userIntegral.getIntegral();
            userIntegralMapper.insert(userIntegral);
        }

        userInfo.setBorrowAuthStatus(borrower.getStatus());
        userInfo.setIntegral(currentIntegral);
        userInfoMapper.updateById(userInfo);
    }
}
