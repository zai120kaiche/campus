package com.campus.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.backend.entity.Collect;
import com.campus.backend.entity.oss;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface FileUploadService extends IService<oss> {

    /*
     * 文件上传
     * 注：阿里云OSS文件上传官方文档链接：https://help.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.749.11987a7dRYVSzn
     * @param: uploadFile
     * @return: string
     */
    oss upload(MultipartFile uploadFile);

    /*
     * 文件下载
     * @param: fileName
     * @param: outputStream
     * @return: void
     */
    Object download(String fileName, HttpServletResponse response) throws UnsupportedEncodingException;

    /*
     * 文件删除
     * @param: objectName
     * @return: java.lang.String
     */
    Object delete(String fileName);
}
