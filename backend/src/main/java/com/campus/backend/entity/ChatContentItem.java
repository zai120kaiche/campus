package com.campus.backend.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatContentItem {
    private String content;
    private boolean isMine;
    private LocalDateTime time;
}
