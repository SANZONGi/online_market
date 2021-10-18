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
    private Long gid;

    @TableId(type = IdType.AUTO)
    private Long oid;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "用户地址不能为空")
    private String useraddress;

    @NotNull(message = "用户电话不能为空")
    @Length(max = 11,min = 11,message = "电话号码为11位")
    @Pattern(regexp = "[0-9]{11}",message = "电话号码格式错误")
    private String userphone;

    @NotNull(message = "订购数量不能为空")
    @Min(value = 0,message = "商品数量错误")
    private Integer number;

    private Double amount;


    private LocalDateTime date;

    @NotNull(message = "订单状态不能为空")
    private Integer status;

}
