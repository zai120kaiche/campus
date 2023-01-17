package com.campus.backend.entity;

import lombok.Data;

@Data
public class ReplyItem extends Reply{
    private String userName;
    private String othersName;
    private boolean likeFlag=false;

    public ReplyItem(Reply reply)
    {
        super(reply);
    }
}
