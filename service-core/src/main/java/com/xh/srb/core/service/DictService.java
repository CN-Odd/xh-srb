package com.xh.srb.core.service;

import com.xh.srb.core.pojo.dto.DictDTO;
import com.xh.srb.core.pojo.dto.ExcelDictDTO;
import com.xh.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
public interface DictService extends IService<Dict> {
    void importData(InputStream inputStream);
    List<ExcelDictDTO> listDictData();

    List<DictDTO> listByParentId(Long parentId);
}
