package com.xh.srb.core.service.impl;

import com.xh.srb.core.pojo.entity.UserLoginRecord;
import com.xh.srb.core.mapper.UserLoginRecordMapper;
import com.xh.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
