package com.campus.backend.entity;

import lombok.Data;

@Data
public class RepliesInfoRequestBody {
    private Integer fid;
    private Integer current;
    private Integer order;
}
