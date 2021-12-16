package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.PriCata;
import com.zjgsu.online_market.service.IPriCataService;
import com.zjgsu.online_market.service.ISecCataService;
import com.zjgsu.online_market.service.impl.PriCataServiceImpl;
import com.zjgsu.online_market.service.impl.SecCataServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    private ISecCataService secCataService;
    private IPriCataService priCataService;

    @Autowired
    public void setSecCataService(SecCataServiceImpl secCataService){
        this.secCataService = secCataService;
    }
    @Autowired
    public void setPriCataService(PriCataServiceImpl priCataService){
        this.priCataService = priCataService;
    }

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
            secCataService.addSec(pri,name);
            return Result.success("ok");
        } catch (Exception e) {
            log.error("错误原因为   " + e.getCause());
            return Result.fail(e.getCause().getMessage(), 1);
        }
    }

    @ApiOperation("在原有一级目录插入二级目录列表")
    @PutMapping("/{pri}/seclist/")
    public Result addSecCataList(@PathVariable @NotNull @Min(1) Integer pri,@RequestParam(value = "name") @NotNull @NotBlank List<String> name) {
        try {
            secCataService.addSecList(pri,name);
        }catch (Exception e) {
            return Result.fail(String.valueOf(e.getCause()));
        }
        return Result.success("ok");
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("插入一级目录项和二级目录列表")
    @PutMapping("/seclist")
    public Result addSecCataList(@NotNull @Min(1) String priName,@RequestParam(value = "name") @NotNull @NotBlank List<String> name) {
        try {
            PriCata priCata = new PriCata();
            priCata.setName(priName);
            priCataService.insertHasKey(priCata);
            secCataService.addSecList(priCata.getId(),name);
        }catch (Exception e) {
            return Result.fail(String.valueOf(e.getMessage()));
        }
        return Result.success("ok");
    }

    @ApiOperation("删除一个二级目录项")
    @DeleteMapping("/{pri}/{sec}")
    public Result deleteSecCata(@PathVariable @NotNull Integer pri,@PathVariable @NotNull Integer sec)
    {
        int rtValue = 1;
        int res = secCataService.deleteSec(pri,sec);
        if (res != rtValue) {
            return Result.fail("没有此项或程序出现异常");
        }
        return Result.success("ok");
    }

}