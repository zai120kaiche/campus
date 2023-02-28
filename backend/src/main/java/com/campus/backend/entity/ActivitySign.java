package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("activitySign")
public class ActivitySign implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer aid;

    private Integer uid;

    private LocalDate date;

    private String pic;


}
