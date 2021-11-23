package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.dto.GoodDto;
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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping("/v2.0")
public class GoodController {

    @Autowired
    private IGoodService goodService;

    @ApiOperation("全部未下架商品")
    @GetMapping("/good")
    public Result list() {
        return Result.success(goodService.list(new QueryWrapper<Good>().ne("status", 2)));
    }

    @ApiOperation("发布商品")
    @LoginRequired(required = true)
    @PutMapping("/good")
    public Result publishGood(@Validated Good good, @NotNull(message = "无图片") List<MultipartFile> files) throws IOException {
        int res = goodService.publish(good, files);
        if (res == 0)
            return Result.success("发布成功");
        else
            return Result.fail("发布失败", res);
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
    @GetMapping("/good/search")
    public Result getGoodBySearch(@NotNull(message = "空参数") @NotBlank(message = "空参数") String val) {
        List<GoodDto> goodDtoList = goodService.getGoodDtoList();
        List<GoodDto> searchRes = goodDtoList
                .stream()
                .filter(goodDto ->
                        (goodDto.getGname().matches(".*" + val + ".*") || goodDto.getDescription().matches(".*" + val + ".*")))
                .collect(Collectors.toList());
        return Result.success(searchRes);
    }


    @ApiOperation("根据一级类别搜索")
    @GetMapping("/good/catalogue/{pri}")
    public Result getGoodByPri(@PathVariable @NotNull(message = "空参数") Integer pri) {
        List<GoodDto> goodDtoList = goodService.getGoodDtoList();
        List<GoodDto> searchRes = goodDtoList
                .stream()
                .filter(goodDto -> goodDto.getPriCata().equals(pri))
                .collect(Collectors.toList());
        return Result.success(searchRes);
    }

    @ApiOperation("根据二级类别搜索")
    @GetMapping("/good/catalogue/{pri}/{sec}")
    public Result getGoodBySec(@PathVariable @NotNull Integer pri, @PathVariable @NotNull Integer sec) {
        List<GoodDto> goodDtoList = goodService.getGoodDtoList();
        List<GoodDto> searchRes = goodDtoList
                .stream()
                .filter(goodDto -> (goodDto.getPriCata().equals(pri) && goodDto.getSecCata().equals(sec)))
                .collect(Collectors.toList());
        return Result.success(searchRes);
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

    @ApiOperation("获取一个带图片的商品类")
    @GetMapping("/good/dto")
    public Result getGoodDtoList() {
        return Result.success(goodService.getGoodDtoList());
    }

}
