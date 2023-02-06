package com.campus.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-02-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer notifier;

    private Integer receiver;

    private Integer outerid;

    private Integer reference;

    private Integer type;

    private LocalDateTime time;

    private Boolean r;

    public Notification(Notification n)
    {
        if(Objects.nonNull(n))
        {
            this.id=n.getId();
            this.notifier=n.getNotifier();
            this.outerid=n.getOuterid();
            this.r=n.getR();
            this.receiver=n.getReceiver();
            this.reference=n.getReference();
            this.time=n.getTime();
            this.type=n.getType();
        }
    }


}
