package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class WhisperPostCommentItem {
    private WhisperFloor floor;
    private List<WhisperReply> replys;

    public WhisperPostCommentItem(WhisperFloor floor, List<WhisperReply> replys) {
        this.floor = floor;
        this.replys = replys;
    }
}
