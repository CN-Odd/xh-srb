package com.xh.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.common.exception.Assert;
import com.xh.common.result.ResponseEnum;
import com.xh.common.util.MD5;
import com.xh.srb.base.util.JwtUtils;
import com.xh.srb.core.mapper.UserAccountMapper;
import com.xh.srb.core.pojo.entity.UserAccount;
import com.xh.srb.core.pojo.entity.UserInfo;
import com.xh.srb.core.mapper.UserInfoMapper;
import com.xh.srb.core.pojo.vo.LoginVO;
import com.xh.srb.core.pojo.vo.RegisterVO;
import com.xh.srb.core.pojo.vo.UserInfoVO;
import com.xh.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserAccountMapper userAccountMapper;
    @Override
    public void register(RegisterVO registerVO) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", registerVO.getMobile());
        Integer count = baseMapper.selectCount(queryWrapper);
        Assert.notTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        UserInfo userInfo = new UserInfo();
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        userInfo.setHeadImg("https://xh-srb-file.oss-cn-hangzhou.aliyuncs.com/avatar/db-2.png");
        userInfo.setName(registerVO.getMobile());
        userInfo.setNickName(registerVO.getMobile());

        baseMapper.insert(userInfo);

        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);
    }

    @Override
    public UserInfoVO login(LoginVO loginVO, String remoteAddr) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        Integer userType = loginVO.getUserType();

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        queryWrapper.eq("user_type", userType);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);

        // 校验账号
        Assert.isNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        String encryptPassword = MD5.encrypt(password);
        Assert.notTrue(encryptPassword.equals(userInfo.getPassword()), ResponseEnum.LOGIN_PASSWORD_ERROR);

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUserType(userType);
        userInfoVO.setName(userInfo.getName());
        userInfoVO.setMobile(userInfo.getMobile());
        userInfoVO.setNickName(userInfo.getNickName());
        userInfoVO.setHeadImg(userInfo.getHeadImg());
        // 生成token
        userInfoVO.setToken(JwtUtils.createToken(userInfo.getId(), userInfo.getName()));


        return userInfoVO;
    }
}
