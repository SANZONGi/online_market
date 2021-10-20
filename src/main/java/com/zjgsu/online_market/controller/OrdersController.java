package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/orders/insert")
    public Result insertOrders(@Validated @NotNull(message = "空对象") Orders orders){
        return ordersService.insertOrders(orders);
    }

    @LoginRequired(required = true)
    @GetMapping("/orders")
    public Result pageList(Integer currentpage,Integer size ) {
        if (currentpage == null || size == null) return Result.fail("空参数");
        return Result.success(ordersService.getOrderPage(currentpage,size));
    }

    @LoginRequired(required = true)
    @GetMapping("/orders/history")
    public Result historyPageList(Integer currentpage, Integer size ) {
        if (currentpage == null || size == null) return Result.fail("空参数");
        IPage iPage = ordersService.getHistoryListPage(currentpage,size);
        return Result.success(iPage);
    }

    @LoginRequired(required = true)
    @GetMapping("/orders/list")
    public Result getList(){
        return Result.success(ordersService.getBaseMapper().selectList(new QueryWrapper<Orders>().eq("status",0)));
    }

    @LoginRequired(required = true)
    @PostMapping("/orders/accept/{oid}")
    public Result accept(@PathVariable(name = "oid") @NotNull(message = "属性值为空") Long oid) {
        if (ordersService.acceptOrder(oid)){
            return Result.success(oid);
        }else {
            return Result.fail("订单无法更改");
        }
    }

    @LoginRequired(required = true)
    @PostMapping("/orders/reject/{oid}")
    public Result reject(@PathVariable(name = "oid") Long oid) {
        if (ordersService.rejectById(oid) == false)
            return Result.fail(400,"订单不存在",1);
        else
            return Result.success(oid);
    }

    @LoginRequired(required = true)
    @PostMapping("/orders/success")
    public Result success(Long oid,Long gid) {
        int flag = ordersService.successById(oid,gid);
        if (flag == 1) {
            return Result.fail(406,"商品已售空",1);
        }else if (flag == 2) {
            return Result.fail(406,"商品下架中",2);
        }else {
            return Result.success(oid);
        }
    }

}
