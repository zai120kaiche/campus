package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class IntentionInfo {
    private int count; //想要的人数
    private List<User> users; //想要的人
}
