package com.campus.backend.entity;

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
 * @since 2023-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("like_floor")
public class LikeFloor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private Integer fid;

    public LikeFloor(Integer uid, Integer fid) {
        this.uid = uid;
        this.fid = fid;
    }
}
