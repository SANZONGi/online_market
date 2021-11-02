package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.dto.PageDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.service.IGoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */

@Validated
@RestController
public class GoodController {

    @Autowired
    private IGoodService goodService;

    @ApiOperation("全部未下架商品")
    @GetMapping("/good")
    public Result list() {
        return Result.success( goodService.list(new QueryWrapper<Good>().ne("status", 2)));
    }

    @ApiOperation("发布商品")
    @LoginRequired(required = true)
    @PutMapping("/good")
    public Result publishGood(@Validated Good good,@NotNull(message = "无图片") List<MultipartFile> files) throws IOException {
        int res = goodService.publish(good,files);
        if (res == 0)
            return Result.success("发布成功");
        else
            return Result.fail("发布失败",res);
    }

    @ApiOperation("根据id获取商品")
    @GetMapping("/good/{gid}")
    public Result getGoodById(@PathVariable(name = "gid") Long gid) {
        Good good = goodService.getGoodById(gid);
        if (good == null)
            return Result.fail("商品不存在");
        return Result.success(good);
    }

    @ApiOperation("根据搜索获取商品(搜索栏)")
    @GetMapping("/good/search/{val}")
    public Result getGoodBySearch(@PathVariable @NotNull(message = "空参数") String val)
    {
        return Result.success("");
    }


    @ApiOperation("根据一级类别搜索")
    @GetMapping("/good/catalogue/{pri}")
    public Result getGoodByPri(@PathVariable @NotNull(message = "空参数") Integer pri)
    {
        return Result.success(goodService.getGoodByPri(pri));
    }

    @ApiOperation("根据二级类别搜索")
    @GetMapping("/good/catalogue/{pri}/{sec}")
    public Result getGoodBySec(@PathVariable @NotNull Integer pri,@PathVariable @NotNull Integer sec)
    {
        return Result.success(goodService.getGoodBySec(pri,sec));
    }

    @ApiOperation("是否存在未下架商品")
    @GetMapping("/good/alive")
    public Result checkGoodExist() {
        return Result.success(goodService.count(new QueryWrapper<Good>().ne("status", 2)) == 0);      //若存在商品的就返回false
    }

    @ApiOperation("获取冻结的商品")
    @LoginRequired(required = true)
    @GetMapping("good/frozen")
    public Result goodInSell() {
       return Result.success(goodService.getFrozenGood());
    }

    @ApiOperation("根据id冻结商品")
    @LoginRequired(required = true)
    @PostMapping("good/frozen/{gid}")
    public Result frozeGood(@PathVariable("gid") @NotNull(message = "gid不能为空") Long gid) {
        return goodService.frozeGoodById(gid);
    }
    @ApiOperation("根据id解冻商品")
    @LoginRequired(required = true)
    @PostMapping("good/unfrozen/{gid}")
    public Result unFrozenGood(@PathVariable("gid") @NotNull(message = "gid不能为空") Long gid) {
        return goodService.unFrozenGood(gid);
    }
    @ApiOperation("查询历史商品")
    @GetMapping("/good/listofhis")
    public Result listOfHis(@Validated PageDto pageDto) {
        if (pageDto == null) return Result.fail("传入空参数");
        Page page = new Page(pageDto.getCurrentpage(), pageDto.getSize());
        IPage iPage = goodService.page(page, new QueryWrapper<Good>().orderByDesc("gid").eq("status", 2));
        return Result.success(200, "查询成功", iPage);
    }

}
