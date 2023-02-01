package com.campus.backend.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatContentItem {
    private Integer id;
    private String content;
    private boolean isMine;
    private LocalDateTime time;
    private boolean withdraw;
}
