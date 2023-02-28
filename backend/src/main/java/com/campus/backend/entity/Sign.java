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
@TableName("sign")
public class Sign implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private LocalDate data;

    public Sign(Integer uid, LocalDate data) {
        this.uid = uid;
        this.data = data;
    }
}
