package com.xh.srb.core.service;

import com.xh.srb.core.pojo.entity.UserBind;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.srb.core.pojo.vo.UserBindVO;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
public interface UserBindService extends IService<UserBind> {

    String commitBind(Long userId, UserBindVO userBindVO);

    void notify(Map<String, Object> parameterMap);
}
