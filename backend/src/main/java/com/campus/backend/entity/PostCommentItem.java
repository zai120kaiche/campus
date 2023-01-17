package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class PostCommentItem {
    private FloorItem floor;
    private List<ReplyItem> replies;

    public PostCommentItem(FloorItem floor, List<ReplyItem> replies) {
        this.floor = floor;
        this.replies = replies;
    }
}
