package com.campus.backend.entity;

public enum OrderType {
    //其中一级评论和二级评论只支持like和date，只有帖子的排序是支持五种。
    Like(0),
    Date(1),
    View(2),
    Comment(3),
    Collect(4);

    private final int key;

    private OrderType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
