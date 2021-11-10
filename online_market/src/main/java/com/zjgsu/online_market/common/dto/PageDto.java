package com.zjgsu.online_market.common.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
public class PageDto {
    @NotNull(message = "当前页id不能为空")
    @Digits(integer = 9, fraction = 0)
    private Long currentpage;

    @NotNull(message = "页大小不能为空")
    @Digits(integer = 9, fraction = 0)
    private Integer size;

}
