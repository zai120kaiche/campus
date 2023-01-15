package com.campus.backend.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private Integer id;

    private String content;

    @TableField(value = "likeNum")
    private Integer likeNum;

    private LocalDateTime date;

    private String location;

    @TableField(value = "parentId")
    private Integer parentId;

    private Integer owner;

    private Integer others;

    private Integer flag;
}
