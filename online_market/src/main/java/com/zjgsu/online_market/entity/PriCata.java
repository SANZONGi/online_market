package com.zjgsu.online_market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@TableName("pri_cata")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PriCata {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotNull
    private String name;
}
