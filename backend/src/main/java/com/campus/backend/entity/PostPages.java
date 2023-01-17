package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class PostPages extends Pages{
    private List<PostItem> records;
}
