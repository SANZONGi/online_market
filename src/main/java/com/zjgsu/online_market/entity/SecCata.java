package com.zjgsu.online_market.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@TableName("sec_cata")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecCata {
    private Integer id;

    @NotNull
    private String name;
}
