package com.xh.srb.core.controller.api;

import com.xh.common.result.R;
import com.xh.srb.core.pojo.dto.DictDTO;
import com.xh.srb.core.pojo.entity.Dict;
import com.xh.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api("字典查询")
@RequestMapping("/api/core/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation("根据节点编号查询下级字典")
    @GetMapping("/findByDict/{dictCode}")
    public R findByDictCode(@PathVariable("dictCode") String code) {
        List<DictDTO> dataList =  dictService.findByDictCode(code);
        return R.ok().setResultData("dataList", dataList);
    }
}
