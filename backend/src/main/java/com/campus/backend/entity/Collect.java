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
 * @since 2023-01-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("collect")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private Integer pid;


}
