package com.campus.backend.entity;

import lombok.Data;

@Data
public class oss {
    //带文件夹的文件名 D:/image/2022/09/19/d174a539-c9a8-4c86-94c4-660bd6693cf3.jpg
    private String uploadImgeUrl;
    //文件名称 d174a539-c9a8-4c86-94c4-660bd6693cf3.jpg
    private String newFileName;
    // 文件访问地址 http://edu-2004-0926.oss-cn-beijing.aliyuncs.com/D:/image/2022/09/19/da8da243-3095-4ef2-8a9a-2be232a482ef.jpg
    private String returnImgeUrl;
}

