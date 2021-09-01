package com.xh.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.srb.core.pojo.entity.Borrower;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.srb.core.pojo.vo.BorrowerApprovalVO;
import com.xh.srb.core.pojo.vo.BorrowerDetailVO;
import com.xh.srb.core.pojo.vo.BorrowerVO;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
public interface BorrowerService extends IService<Borrower> {

    void saveBorrowerVOByUserId(BorrowerVO borrowerVO, Long userId);

    Integer getBorrowerStatusByUserId(Long userId);

    IPage<Borrower> listPage(Page<Borrower> page, String keyword);

    BorrowerDetailVO showBorrowerDetail(Integer id);

    void approval(BorrowerApprovalVO borrowerApprovalVO);
}
