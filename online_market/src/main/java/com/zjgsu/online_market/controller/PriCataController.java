package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.service.IPriCataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v2.0")
public class PriCataController {
    @Autowired
    private IPriCataService priCataService;

    @ApiOperation("根据id获取一级目录")
    @GetMapping("/pri")
    public Result getAll() {
        return Result.success(priCataService.getAll());
    }

    @ApiOperation("同时插入一二级目录")
    @PutMapping("/pri_sec")
    public Result addPriAndSec(@NotNull @NotBlank String priName,@NotNull @NotBlank String secName)
    {
        int res = priCataService.insertPriAndSec(priName,secName);
        if (res == 1)
        {
            return Result.fail("请检查一级目录名是否正确或者重复");
        }else if (res == 2)
        {
            return Result.fail("请检查二级目录名是否正确或者重复");
        }
        return Result.success("ok");
    }
}
