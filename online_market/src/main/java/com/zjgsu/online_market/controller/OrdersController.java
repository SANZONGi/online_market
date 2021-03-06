package com.zjgsu.online_market.controller;


import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.dto.PageDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.service.IOrdersService;
import com.zjgsu.online_market.service.impl.OrdersServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 * 订单情况：0  等待中
 * 1  交易中
 * 2  交易成功
 * 3  交易失败
 * 货物：0
 **/
@RestController
@RequestMapping("/v2.0")
public class OrdersController {

    private IOrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersServiceImpl ordersService){
        this.ordersService = ordersService;
    }

    @ApiOperation("创建订单")
    @LoginRequired(required = true)
    @PutMapping("/orders")
    public Result insertOrders(@Validated @NotNull(message = "空对象") Orders orders) {
        int res = ordersService.insertOrders(orders);
        if (res == 1) {
            return Result.fail("创建失败", 1);
        }
        return Result.success("创建成功");
    }

    @ApiOperation(value = "获取订单信息（分页|指定用户|指定状态）"
            , notes = "若不输入分页信息，则不分页\n若不输入用户则不指定用户\n若不输入状态，默认搜索全部状态")
    @GetMapping("/orders")
    public Result getOrders(PageDto pageDto, @Min(1) Long uid, @RequestParam(value = "status", required = false) List<Integer> status) {
        if (pageDto.getCurrentpage() != null || pageDto.getSize() != null) {
            if (pageDto.getCurrentpage() == null || pageDto.getSize() == null)
            {
                return Result.fail("分页参数不全", 1);
            }
        }
        if (status != null) {
            for (Integer i : status) {
                if (i > OrdersServiceImpl.ORDER_FAIL || i < OrdersServiceImpl.ORDER_WAITING)
                {
                    return Result.fail("状态异常", 2);
                }
            }
            return Result.success(ordersService.
                    getOrdersAndUsersPageWithStatus(pageDto.getCurrentpage(), pageDto.getSize(), uid, status));
        } else {
            return Result.success(ordersService.
                    getOrdersAndUsersPageWithStatus(pageDto.getCurrentpage(), pageDto.getSize(), uid, ordersService.getAllStatus()));
        }
    }


    @Deprecated
    @ApiOperation("查询所有未完成订单(分页)")
    @GetMapping("/orders/unsolved")
    public Result getUnsolved(@Validated PageDto pageDto) {
        ArrayList<Integer> statusList = new ArrayList<>(6);
        statusList.add(OrdersServiceImpl.ORDER_EXCHANGING);
        statusList.add(OrdersServiceImpl.ORDER_WAITING);
        return Result.success(ordersService.getOrdersAndUsersPageWithStatus(pageDto.getCurrentpage(), pageDto.getSize(), null, statusList));
    }

    @Deprecated
    @ApiOperation("查询所有历史订单")
    @GetMapping("/orders/history")
    public Result allHistory(@Validated @NotNull PageDto pageDto) {
        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(OrdersServiceImpl.ORDER_FAIL);
        statusList.add(OrdersServiceImpl.ORDER_SUCCESS);
        return Result.success(ordersService.getOrdersAndUsersPageWithStatus(pageDto.getCurrentpage(), pageDto
                .getSize(), null, statusList));
    }

    @Deprecated
    @ApiOperation("查询用户历史订单")
    @GetMapping("/orders/history/{uid}")
    public Result getUserHistory(@PathVariable @NotNull @Min(1) Long uid, @Validated @NotNull PageDto pageDto) {
        ArrayList<Integer> statusList = new ArrayList<>(6);
        statusList.add(OrdersServiceImpl.ORDER_FAIL);
        statusList.add(OrdersServiceImpl.ORDER_SUCCESS);
        return Result.success(ordersService.getOrdersAndUsersPageWithStatus(pageDto.getCurrentpage(), pageDto
                .getSize(), uid, statusList));
    }

    @Deprecated
    @ApiOperation("获取等待中的订单(分页)")
    @GetMapping("/orders/waiting")
    public Result getWaitingList(@Validated @NotNull PageDto pageDto) {
        ArrayList<Integer> statusList = new ArrayList<>(6);
        statusList.add(OrdersServiceImpl.ORDER_WAITING);
        return Result.success(
                ordersService.getOrdersAndUsersPageWithStatus(pageDto.getCurrentpage(), pageDto.getSize(), null, statusList));
    }

    @ApiOperation("接受订单")
    @LoginRequired(required = true)
    @PostMapping("/orders/accept/{oid}")
    public Result accept(@PathVariable @NotNull(message = "oid为空") Long oid, @NotNull(message = "gid为空") Long gid) {
        int res = ordersService.acceptOrder(oid, gid);
        if (res == 0) {
            return Result.success("成功");
        }
        if (res == 1) {
            return Result.fail("商品不足", 1);
        } else if (res == 2) {
            return Result.fail("订单无法更改", 2);
        } else {
            return Result.fail("失败", res);
        }
    }

    @ApiOperation("拒绝订单")
    @LoginRequired(required = true)
    @PostMapping("/orders/reject/{oid}")
    public Result reject(@PathVariable @NotNull(message = "oid为空") Long oid, @NotNull Long gid) {
        int res = ordersService.rejectById(oid, gid);
        if (res == 1)
        {
            return Result.fail( "订单错误", res);
        }
        else if (res == 2) {
            return Result.fail("商品错误", res);
        }
        return Result.success(oid);
    }

    @ApiOperation("完成订单")
    @LoginRequired(required = true)
    @PostMapping("/orders/success/{oid}")
    public Result success(@PathVariable @NotNull(message = "oid为空") Long oid, @NotNull(message = "oid为空") Long gid) {
        int res = ordersService.successById(oid, gid);
        if (res == 1) {
            return Result.fail("订单已结束", res);
        }
        return Result.success(gid);
    }

}
