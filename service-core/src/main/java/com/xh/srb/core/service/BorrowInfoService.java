package com.xh.srb.core.service;

import com.xh.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.srb.core.pojo.vo.BorrowInfoVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
public interface BorrowInfoService extends IService<BorrowInfo> {

    BigDecimal getBorrowAmount(Long userId);

    void saveBorrowInfoByUserId(BorrowInfoVO borrowInfoVO, Long userId);

    Integer getBorrowInfoStatus(Long userId);

    List<BorrowInfo> selectList();
}
