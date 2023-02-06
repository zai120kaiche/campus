package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("activity_collect")
public class ActivityCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid")
    private Integer uid;
    @TableId(value = "aid")
    private Integer aid;


}
