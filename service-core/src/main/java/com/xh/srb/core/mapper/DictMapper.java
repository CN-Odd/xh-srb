package com.xh.srb.core.mapper;

import com.xh.srb.core.pojo.dto.ExcelDictDTO;
import com.xh.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDTO> list);
}
