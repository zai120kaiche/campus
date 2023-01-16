package com.campus.backend.entity;

import lombok.Data;

@Data
public class LikeType {
    //用户id
    private Integer uid;
    //点赞内容的id
    private Integer objectId;
    //内容所属的类型
    private Integer type;
    //true为点赞，false为取消点赞
    private boolean flag;
}
