package com.xh.srb.core.service;

import com.xh.srb.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.srb.core.pojo.vo.LoginVO;
import com.xh.srb.core.pojo.vo.RegisterVO;
import com.xh.srb.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
public interface UserInfoService extends IService<UserInfo> {

    void register(RegisterVO registerVO);

    UserInfoVO login(LoginVO loginVO, String remoteAddr);
}
