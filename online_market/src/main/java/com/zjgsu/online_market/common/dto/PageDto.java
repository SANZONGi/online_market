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

    public boolean pageValid(PageDto pageDto)
    {
        if (pageDto.getSize() == null || pageDto.getCurrentpage() == null) {
            throw new RuntimeException("page error");
        }
        if ((pageDto.getCurrentpage() - 1) * pageDto.getSize() < 0) {
            throw new RuntimeException("page error");
        }
        return true;
    }
}
