package com.zjgsu.online_market.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@TableName(value = "img")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Img {

    public Img(@NotNull Long gid, @NotNull String url) {
        this.gid = gid;
        this.url = url;
    }

    @TableId(type = IdType.AUTO)
    @NotNull
    private Long id;

    @TableField(value = "gid")
    @NotNull
    private Long gid;

    @TableField(value = "img_url")
    @NotNull
    private String url;
}
