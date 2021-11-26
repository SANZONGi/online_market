package com.zjgsu.online_market.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GoodDto {

    private Long uid;

    private Long gid;

    private String gname;

    private Double price;

    private Integer stock;

    private Integer status;

    private String description;

    private Integer priCata;

    private Integer secCata;

    private String imgUrl;
}
