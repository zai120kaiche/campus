package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Objects;

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
@TableName("FLOOR")
public class Floor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    @TableField("likeNum")
    private Integer likeNum;

    @TableField("replyNum")
    private Integer replyNum;

    @TableField("DATE")
    private LocalDateTime date;

    private String location;

    private Integer pid;

    @TableField("OWNER")
    private Integer owner;

    public Floor(Floor floor)
    {
        if(Objects.nonNull(floor))
        {
            this.content=floor.content;
            this.date=floor.date;
            this.id=floor.id;
            this.likeNum=floor.likeNum;
            this.location=floor.location;
            this.owner=floor.owner;
            this.pid=floor.pid;
            this.replyNum=floor.replyNum;
        }
    }

}
