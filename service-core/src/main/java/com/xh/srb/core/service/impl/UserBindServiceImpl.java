package com.xh.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.common.exception.Assert;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.core.enums.UserBindEnum;
import com.xh.srb.core.hfb.FormHelper;
import com.xh.srb.core.hfb.HfbConst;
import com.xh.srb.core.hfb.RequestHelper;
import com.xh.srb.core.mapper.UserInfoMapper;
import com.xh.srb.core.pojo.entity.UserBind;
import com.xh.srb.core.mapper.UserBindMapper;
import com.xh.srb.core.pojo.entity.UserInfo;
import com.xh.srb.core.pojo.vo.UserBindVO;
import com.xh.srb.core.service.UserBindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public String commitBind(Long userId, UserBindVO userBindVO) {
        String mobile = userBindVO.getMobile();
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Assert.notTrue(userInfo.getMobile().equals(mobile), ResponseEnum.MOBILE_NOT_BELONG_YOU);

        QueryWrapper<UserBind> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_card", userBindVO.getIdCard());
        UserBind userBind = baseMapper.selectOne(queryWrapper);
        if (null != userBind) {
            Assert.notTrue(userBind.getUserId().equals(userId), ResponseEnum.USER_BIND_ID_CARD_EXIST_ERROR);
            Assert.notTrue(!userBind.getStatus().equals(UserBindEnum.BINK_OK.getCode()), ResponseEnum.USER_BINDED);
            BeanUtils.copyProperties(userBindVO, userBind);
            baseMapper.updateById(userBind);
        } else {
            userBind = new UserBind();
            BeanUtils.copyProperties(userBindVO, userBind);
            userBind.setUserId(userId);
            userBind.setStatus(UserBindEnum.NO_BIND.getCode());
            baseMapper.insert(userBind);
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentUserId", userId);
        paramMap.put("idCard", userBindVO.getIdCard());
        paramMap.put("personalName", userBindVO.getName());
        paramMap.put("bankType", userBindVO.getBankType());
        paramMap.put("bankNo", userBindVO.getBankNo());
        paramMap.put("mobile", userBindVO.getMobile());
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        String buildForm = FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
        return buildForm;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notify(Map<String, Object> parameterMap) {
        String resultCode = (String) parameterMap.get("resultCode");
        String resultMsg = (String) parameterMap.get("resultMsg");
        Assert.notTrue(resultCode != "0001", ResponseEnum.BIND_ERROR);


        String bindCode = (String) parameterMap.get("bindCode");
        String agentUserId = (String) parameterMap.get("agentUserId");

        QueryWrapper<UserBind> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", agentUserId);
        UserBind userBind = baseMapper.selectOne(queryWrapper);
        Assert.isNull(userBind,  ResponseEnum.BIND_ERROR);

        userBind.setBindCode(bindCode);
        userBind.setStatus(UserBindEnum.BINK_OK.getCode());
        baseMapper.updateById(userBind);


        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", userBind.getUserId());
        UserInfo userInfo = userInfoMapper.selectOne(userQueryWrapper);
        userInfo.setIdCard(userBind.getIdCard());
        userInfo.setName(userBind.getName());
        userInfo.setBindStatus(userBind.getStatus());
        userInfo.setBindCode(bindCode);
        userInfoMapper.updateById(userInfo);

    }
}
