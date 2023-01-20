package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("identifier")
public class Identifier implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("phoneOrEmail")
    private String phoneOrEmail;

    private Integer identifier;


}
