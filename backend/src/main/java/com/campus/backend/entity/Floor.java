package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Floor {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    @TableField(value = "likeNum")
    private Integer likeNum;

    @TableField(value = "replyNum")
    private Integer replyNum;

    private LocalDateTime date;

    private String location;

    private Integer pid;

    private Integer owner;
}