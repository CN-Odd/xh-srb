package com.xh.srb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.xh.common.exception.BusinessException;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.core.pojo.dto.DictDTO;
import com.xh.srb.core.pojo.dto.ExcelDictDTO;
import com.xh.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("admin/core/dict")
@CrossOrigin
public class AdminDictController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "Excel批量导入数据字典")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(value = "excel文件", required = true)
            @RequestParam("file") MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            dictService.importData(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "获取数据字典")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("mydict", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(outputStream, ExcelDictDTO.class).sheet("数据字典").doWrite(dictService.listDictData());
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.EXPORT_DATA_ERROR);
        }
    }

    @ApiOperation(value = "根据父id获取字典")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(@ApiParam(value = "父id")
                            @PathVariable(name = "parentId") Long parentId) {
        List<DictDTO> dicts = dictService.listByParentId(parentId);
        return R.ok().setResultData("list", dicts);
    }



}
