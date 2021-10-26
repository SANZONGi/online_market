package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.dto.PageDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

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

    @GetMapping("/home")
    public Result list() {
        return Result.success( goodService.list(new QueryWrapper<Good>().ne("status", 2)));
    }

    @LoginRequired(required = true)
    @PostMapping("/publishGood")
    public Result publishGood(@Validated Good good) {
        if (goodService.publish(good))
            return Result.success("发布成功");
        else
            return Result.fail("发布失败");
    }

    @GetMapping("/good/{gid}")
    public Result getGoodById(@PathVariable(name = "gid") Long gid) {
        Good good = goodService.getGoodById(gid);
        if (good == null)
            return Result.fail("商品不存在");
        return Result.success(good);
    }
    @GetMapping("/good/alive")
    public Result checkGoodExist() {
        return Result.success(goodService.count(new QueryWrapper<Good>().ne("status", 2)) == 0);      //若存在商品的就返回false
    }

    @LoginRequired(required = true)
    @GetMapping("good/frozen")
    public Result goodInSell() {
       return Result.success(goodService.getFrozenGood());
    }

    @LoginRequired(required = true)
    @PostMapping("good/frozen/{gid}")
    public Result frozeGood(@PathVariable("gid") @NotNull(message = "gid不能为空") Long gid) {
        return goodService.frozeGoodById(gid);
    }
    @LoginRequired(required = true)
    @PostMapping("good/unfrozen/{gid}")
    public Result unFrozenGood(@PathVariable("gid") @NotNull(message = "gid不能为空") Long gid) {
        return goodService.unFrozenGood(gid);
    }
    @GetMapping("/good/listofhis")
    public Result listOfHis(@Validated PageDto pageDto) {
        if (pageDto == null) return Result.fail("传入空参数");
        Page page = new Page(pageDto.getCurrentpage(), pageDto.getSize());
        IPage iPage = goodService.page(page, new QueryWrapper<Good>().orderByDesc("gid").eq("status", 2));
        return Result.success(200, "查询成功", iPage);
    }

}
