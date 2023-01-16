package com.campus.backend.entity;

import lombok.Data;

@Data
public class PostInfoRequestBody {
    private Integer pid;
    private Integer current;
    private Integer order;
}
