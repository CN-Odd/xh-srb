package com.xh.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.srb.core.listener.ExcelDictDTOListener;
import com.xh.srb.core.pojo.dto.DictDTO;
import com.xh.srb.core.pojo.dto.ExcelDictDTO;
import com.xh.srb.core.pojo.entity.Dict;
import com.xh.srb.core.mapper.DictMapper;
import com.xh.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Odd
 * @since 2021-07-25
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet(0).doRead();
        log.info("importData finished");
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dicts = baseMapper.selectList(null);
        List<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dicts.size());
        dicts.forEach(recoder -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(recoder, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public List<DictDTO> listByParentId(Long parentId) {
        List<Dict> dicts = null;
        List<DictDTO> dictDTOS = null;
        String key = "srb:core:dictList:" + parentId;
        try {
            dictDTOS = (List<DictDTO>) redisTemplate.opsForValue().get(key);
            log.info("从redis取值，key：" + key);
        } catch (Exception e) {
            //此处不抛出异常，继续执行后面的代码
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }
        if (dictDTOS == null) {
            QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("parent_id", parentId);
            dicts = baseMapper.selectList(queryWrapper);
            dictDTOS = new ArrayList<>(dicts.size());
            List<DictDTO> finalDictDTOS = dictDTOS;
            dicts.forEach(dict -> {
                DictDTO dictDTO = new DictDTO();
                BeanUtils.copyProperties(dict, dictDTO);
                this.hasChildren(dictDTO);
                finalDictDTOS.add(dictDTO);
            });
            try {
                redisTemplate.opsForValue().set(key, dictDTOS);
            } catch (RedisException e) {
                log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
            }
        }
        return dictDTOS;
    }

    @Override
    public List<DictDTO> findByDictCode(String code) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code", code);
        Dict dict = baseMapper.selectOne(queryWrapper);
        List<DictDTO> dictDTOS = this.listByParentId(dict.getId());
        return dictDTOS;
    }

    @Override
    public String getNameByParentDictCodeAndValue(String dictCode, Integer value) {
//        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("dict_code", dictCode);
//        Dict dict = baseMapper.selectOne(queryWrapper);
        List<DictDTO> dictDTOS = this.findByDictCode(dictCode);
        String dictName = null;
        for(DictDTO dictDTO : dictDTOS) {
            if (dictDTO.getValue().equals(value)) {
                dictName = dictDTO.getName();
                break;
            }
        }
        return dictName;
    }

    private void hasChildren(DictDTO dictDTO) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("parent_id", dictDTO.getId());
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count == 0) {
            dictDTO.setHasChildren(false);
        } else {
            dictDTO.setHasChildren(true);
        }
    }
}
