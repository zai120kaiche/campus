package com.campus.backend.entity;

import lombok.Data;

@Data
public class PostInfoRequestBody {
    private Integer uid;//当前用户的id，可为空。
    private Integer pid;
    private Integer current;
    private Integer order;
}
