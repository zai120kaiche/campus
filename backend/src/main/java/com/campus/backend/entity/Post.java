package com.campus.backend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    @TableField(value = "likeNum")
    private Integer likeNum;

    @TableField(value = "viewNum")
    private Integer viewNum;

    @TableField(value = "commentNum")
    private Integer commentNum;

    @TableField(value = "collectNum")
    private Integer collectNum;

    @TableField(value = "forwardNum")
    private Integer forwardNum;

    private String location;

    private LocalDateTime date;

    private Integer owner;

    private Integer kind;
}