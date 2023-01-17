package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Objects;

import javafx.geometry.Pos;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
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

    private String kind;

    @TableField("kindName")
    private String kindName;

    private Integer university;

    private String pic;

    public Post(Post post)
    {
        if (Objects.nonNull(post))
        {
            this.collectNum=post.collectNum;
            this.commentNum=post.commentNum;
            this.content=post.content;
            this.date=post.date;
            this.forwardNum=post.forwardNum;
            this.id=post.id;
            this.kind=post.kind;
            this.kindName=post.kindName;
            this.likeNum=post.likeNum;
            this.location=post.location;
            this.owner=post.owner;
            this.pic=post.pic;
            this.title=post.title;
            this.university=post.university;
            this.viewNum=post.viewNum;
        }
    }

}
