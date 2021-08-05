package com.xh.srb.core.service.impl;

import com.xh.srb.core.pojo.entity.UserInfo;
import com.xh.srb.core.mapper.UserInfoMapper;
import com.xh.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
