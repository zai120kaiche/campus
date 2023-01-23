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
 * @since 2023-01-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("commodity")
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    @TableField("viewNum")
    private Integer viewNum;

    @TableField("collectNum")
    private Integer collectNum;

    //true表示卖，false表示买
    @TableField("tradeFlag")
    private Boolean tradeFlag;

    @TableField("finishFlag")
    private Boolean finishFlag;

    private LocalDateTime date;

    private Integer owner;

    private String location;

    private String kind;

    @TableField("kindName")
    private String kindName;

    private Integer university;

    private String pic;

    private Integer price;



    public Commodity(Commodity commodity)
    {
        if(Objects.nonNull(commodity))
        {
            this.collectNum=commodity.collectNum;
            this.content=commodity.content;
            this.date=commodity.date;
            this.tradeFlag=commodity.tradeFlag;
            this.finishFlag=commodity.finishFlag;
            this.id=commodity.id;
            this.kind=commodity.kind;
            this.kindName=commodity.kindName;
            this.location=commodity.location;
            this.owner=commodity.owner;
            this.pic=commodity.pic;
            this.university=commodity.university;
            this.viewNum=commodity.viewNum;
            this.price = commodity.price;
        }
    }

}
