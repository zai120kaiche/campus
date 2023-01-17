package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class ReplyPages extends Pages{
    private List<ReplyItem> records;
}
