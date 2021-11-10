package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.service.ISecCataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v2.0")
public class SecCataController {
    @Autowired
    private ISecCataService secCataService;

    @ApiOperation("获取全部二级目录")
    @GetMapping("/sec")
    public Result getAll() {
        return Result.success(secCataService.getAll());
    }

    @ApiOperation("获取一级目录下所有二级目录")
    @GetMapping("/sec/{pri}")
    public Result getAllInPri(@PathVariable @NotNull @Min(1) Integer pri)
    {
        return Result.success(secCataService.getAllInPri(pri));
    }
}
