package com.xh.srb.core.service;

import com.xh.srb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {

    List<UserLoginRecord> listTop50(Long userId);
}
