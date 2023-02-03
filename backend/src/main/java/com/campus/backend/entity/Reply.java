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
@TableName("reply")
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    @TableField("likeNum")
    private Integer likeNum;

    @TableField("DATE")
    private LocalDateTime date;

    private String location;

    @TableField("OWNER")
    private Integer owner;

    @TableField("OTHERS")
    private Integer others;

    private Integer reference;

    private Integer fid;

    public Reply(Reply reply)
    {
        if(Objects.nonNull(reply))
        {
            this.content=reply.content;
            this.date=reply.date;
            this.fid=reply.fid;
            this.id=reply.id;
            this.likeNum=reply.likeNum;
            this.location=reply.location;
            this.others=reply.others;
            this.owner=reply.owner;
            this.reference=reply.reference;
        }
    }

}
