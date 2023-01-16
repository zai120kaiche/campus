package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class PostCommentItem {
    private Floor floor;
    private List<Reply> replies;

    public PostCommentItem(Floor floor, List<Reply> replies) {
        this.floor = floor;
        this.replies = replies;
    }
}
