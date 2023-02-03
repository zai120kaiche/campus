package com.campus.backend.entity;

import lombok.Data;

@Data
public class NotificationItem extends Notification{
    private int pid;
    private String postUsername;
    private String title;

    private int fid;
    private String floorUsername;
    private String floorContent;

    private int rid;
    private String replyUserName;
    private String othersName;
    private String replyContent;

    private String username;
    private String content;
}
