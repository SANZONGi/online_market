package com.zjgsu.online_market.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */

@Data
@TableName(value = "good")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Good implements Serializable {

    @NotNull(message = "卖家id不能为空")
    private Long uid;

    @TableId(type = IdType.AUTO)
    private Long gid;

    private String gname;

    @NotNull(message = "图片不能为空")
    @NotBlank(message = "图片不能为空")
    private String image;

    @NotNull(message = "价格不能为空")
    private Double price;


    private Integer stock;

    @NotNull(message = "状态不能为空")
    private Integer status;

    private String description;


}
