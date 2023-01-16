package com.campus.backend.entity;

import lombok.Data;

@Data
public class CollectInfoRequestBody {
    private Integer uid;
    private Integer current;
    private Integer order;
}
