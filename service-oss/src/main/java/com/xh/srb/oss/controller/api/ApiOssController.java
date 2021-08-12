package com.xh.srb.oss.controller.api;

import com.xh.common.exception.BusinessException;
import com.xh.common.result.R;
import com.xh.common.result.ResponseEnum;
import com.xh.srb.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api(tags = "阿里云存储")
@CrossOrigin
@RestController
@RequestMapping("/api/oss")
public class ApiOssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam("文件")
            @RequestParam("file") MultipartFile file,
            @ApiParam("所属模块")
            @RequestParam("model") String model) {

        String url = null;
        try {
            String filename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            url = ossService.upload(filename, model, inputStream);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }


        return R.ok().setResultData("url", url);

    }

    @ApiOperation("根据url删除文件")
    @DeleteMapping("/remove")
    public R remove(
            @ApiParam(value = "要删除的文件url")
            @RequestParam("url") String url) {
        ossService.removeFile(url);
        return R.ok();
    }
}
