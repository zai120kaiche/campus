package com.campus.backend.entity;

import lombok.Data;

@Data
public class RepliesInfoRequestBody {
    private Integer uid;//当前用户的id，可为空。
    private Integer fid;
    private Integer current;
    private Integer order;
}
