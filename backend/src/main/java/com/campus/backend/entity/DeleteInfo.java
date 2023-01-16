package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class DeleteInfo {
    private Integer uid;
    private List<Integer> objectIds;
    //类型，删除评论的时候使用，此时ids只能 包含一个id
    private Integer type;
}
