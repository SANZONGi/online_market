package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.dto.PageDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.service.IOrdersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 * 订单情况：0  等待中
 *         1  交易中
 *         2  交易成功
 *         3  交易失败
 * 货物：0
 **/
@RestController
public class OrdersController {
    @Autowired
    private IOrdersService ordersService;

    @ApiOperation("创建订单")
    @LoginRequired(required = true)
    @PutMapping("/orders")
    public Result insertOrders(@Validated @NotNull(message = "空对象") Orders orders){
        int res = ordersService.insertOrders(orders);
        if (res == 1)
        {
            return Result.fail("创建失败",1);
        }
        return Result.success("创建成功");
    }

    @ApiOperation("查询所有订单")
    @GetMapping("/orders")
    public Result pageList(@Validated PageDto pageDto) {
        return Result.success(ordersService.getOrderPage(pageDto.getCurrentpage(),pageDto.getSize()));
    }
    @ApiOperation("查询所有历史订单")
    @GetMapping("/orders/history")
    public Result allHistory(@Validated @NotNull PageDto pageDto) {
        IPage iPage = ordersService.getHistoryListPage(pageDto.getCurrentpage(),pageDto.getSize(),null);
        return Result.success(iPage);
    }
    @ApiOperation("查询用户历史订单")
    @GetMapping("/orders/history/{uid}")
    public Result getUserHistory(@PathVariable @NotNull @Min(1) Long uid, @Validated @NotNull PageDto pageDto) {
        IPage iPage = ordersService.getHistoryListPage(pageDto.getCurrentpage(),pageDto.getSize(),uid);
        return Result.success(iPage);
    }

    @ApiOperation("获取未接受的订单")
    @GetMapping("/orders/list")
    public Result getList(){
        return Result.success(ordersService.list(new QueryWrapper<Orders>().eq("status",0)));
    }

    @ApiOperation("接受订单")
    @LoginRequired(required = true)
    @PostMapping("/orders/accept/{oid}")
    public Result accept(@PathVariable @NotNull(message = "oid为空") Long oid,@NotNull(message = "gid为空") Long gid) {
        int res = ordersService.acceptOrder(oid,gid);
        if (res == 0) {
            return Result.success("成功");
        }
        if (res == 1){
            return Result.fail("无此商品",1);
        }else if (res == 2){
            return Result.fail("订单无法更改",2);
        } else{
            return Result.fail("失败",res);
        }
    }

    @ApiOperation("拒绝订单")
    @LoginRequired(required = true)
    @PostMapping("/orders/reject/{oid}")
    public Result reject(@PathVariable @NotNull(message = "oid为空") Long oid,@NotNull Long gid) {
        int res = ordersService.rejectById(oid,gid);
        if (res == 1)
            return Result.fail(400,"商品不存在",res);
        else if (res == 2)
            return Result.fail(400,"订单不存在",res);
        return Result.success(oid);
    }

    @ApiOperation("完成订单")
    @LoginRequired(required = true)
    @PostMapping("/orders/success/{oid}")
    public Result success(@PathVariable @NotNull(message = "oid为空") Long oid,@NotNull(message = "oid为空") Long gid) {
        int res = ordersService.successById(oid,gid);
        if (res == 1) {
            return Result.fail(406,"无此商品",res);
        } else if (res == 2){
            return Result.fail("更新失败",res);
        }
        return Result.success(gid);
    }

}
