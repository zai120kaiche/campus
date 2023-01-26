package com.campus.backend.entity;

import lombok.Data;

@Data
public class ChatRequestBody {
    private Integer current;
    private Integer send;
    private Integer recv;
}
