package com.campus.backend.controller;


import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.oss;
import com.campus.backend.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Api(description = "阿里云OSS文件上传、下载、删除API")
@CrossOrigin
@RequestMapping("api/pri/file")
@RestController
public class OssFileController {
    @Autowired
    private FileUploadService fileUploadService;

    /*
     * 文件上传api
     * @param: file
     * @return: com.alibaba.fastjson.JSONObject
     * @create: 2020/10/31 17:35
     * @author: beisheng
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public Object upload(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            oss returnFileUrl = fileUploadService.upload(file);
            if (returnFileUrl.equals("error")) {
                return Result.fail("图片类型不符合");
            }
            return Result.succ(returnFileUrl);
        } else {
            return Result.fail("图片上传失败");
        }
    }

    /*
     * 文件下载api
     * @param: fileName
     * @param: response
     * @return: com.alibaba.fastjson.JSONObject
     * @create: 2020/10/31 17:35
     * @author: beisheng
     */
    @ApiOperation(value = "文件下载")
    @GetMapping(value = "download/{fileName}")
    public Object download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception {

        String status = String.valueOf(fileUploadService.download(fileName, response));
        if (status.equals("error")) {
            return Result.fail("文件下载失败");
        } else {
            return Result.succ("文件下载成功");
        }
    }

    /*
     * 文件删除api
     * @param: fileName
     * @return: com.alibaba.fastjson.JSONObject
     * @create: 2020/10/31 17:35
     * @author: beisheng
     */
    @ApiOperation(value = "文件删除")
    @GetMapping("/delete/{fileName}")
    public Object DeleteFile(@PathVariable("fileName") String fileName) {

        String status = String.valueOf(fileUploadService.delete(fileName));
        if (status.equals("error")) {
            return Result.fail("文件下载失败");
        } else {
            return Result.succ("文件下载成功");
        }
    }
}

