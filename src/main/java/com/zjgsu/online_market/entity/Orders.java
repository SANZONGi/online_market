package com.zjgsu.online_market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orders implements Serializable {

    @NotNull(message = "商品id不能为空")
    @Min(value = 1)
    private Long gid;

    @TableId(type = IdType.AUTO)
    private Long oid;

    @NotNull(message = "用户id不能为空")
    @Min(value = 1)
    private Long uid;

    @NotNull(message = "订购数量不能为空")
    @Min(value = 1)
    private Integer number;

    private Double amount;


    private LocalDateTime date;

    @NotNull(message = "订单状态不能为空")
    private Integer status;

}
