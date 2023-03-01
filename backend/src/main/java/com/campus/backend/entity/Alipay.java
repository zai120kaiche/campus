package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author campus
 * @since 2023-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("Alipay")
public class Alipay implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull(message = "商品名称不能空")
    String subject;
    @NotNull(message = "付款金额不能空")
    String total_amount;
    String body;

}
