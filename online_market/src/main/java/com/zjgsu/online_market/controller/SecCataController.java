package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.SecCata;
import com.zjgsu.online_market.service.ISecCataService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author SANZONG
 */
@Slf4j
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
    public Result getAllInPri(@PathVariable @NotNull @Min(1) Integer pri) {
        return Result.success(secCataService.getAllInPri(pri));
    }

    @ApiOperation("在原有一级目录插入二级目录")
    @PutMapping("/sec/{pri}")
    public Result addSecCata(@PathVariable @NotNull @Min(1) Integer pri, @NotNull @NotBlank String name) {
        try {
            secCataService.getBaseMapper().insert(new SecCata().setPriId(pri).setName(name));
            return Result.success("ok");
        } catch (Exception e) {
            log.error("错误原因为   " + e.getCause());
            return Result.fail(e.getCause().getMessage(), 1);
        }
    }

    @ApiOperation("在原有一级目录插入二级目录列表")
    @PutMapping("/seclist/{pri}")
    public Result addSecCataList(@PathVariable @NotNull @Min(1) Integer pri, @NotNull @NotBlank List<String> name) {
        try {
            for (String item : name) {
                addSecCata(pri, item);
            }
        }catch (Exception e) {
            return Result.fail(String.valueOf(e.getCause()));
        }
        return Result.success("ok");
    }
}