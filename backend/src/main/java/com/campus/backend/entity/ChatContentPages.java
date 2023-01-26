package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class ChatContentPages extends Pages{
    private List<ChatContentItem> records;
}
