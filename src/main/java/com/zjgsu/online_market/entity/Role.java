package com.zjgsu.online_market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@TableName(value = "img")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role {

    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    @TableField(value = "uid")
    @NotNull
    private Long uid;

    @TableField(value = "role")
    @NotNull
    private Integer role;
}
