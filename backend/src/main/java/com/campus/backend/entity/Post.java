package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author campus
 * @since 2023-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    @TableField("likeNum")
    private Integer likeNum;

    @TableField("viewNum")
    private Integer viewNum;

    @TableField("commentNum")
    private Integer commentNum;

    @TableField("collectNum")
    private Integer collectNum;

    @TableField("forwardNum")
    private Integer forwardNum;

    private String location;

    @TableField("DATE")
    private LocalDateTime date;

    @TableField("OWNER")
    private Integer owner;

    private Integer kind;


}
