package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.service.impl.GoodServiceImpl;
import com.zjgsu.online_market.service.impl.OrdersServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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
    private OrdersServiceImpl ordersService;

    @Autowired
    private GoodServiceImpl goodService;

    @PostMapping("/orders/insert")
    public Result insertOrders(HttpServletRequest request, Orders orders){
        if (orders == null)
        {
            return Result.fail(204,"请正确输入",orders);
        }
        LocalDateTime now = LocalDateTime.now();
        orders.setDate(now);
        orders.setOid(null);
        ordersService.getBaseMapper().insert(orders);
        return Result.success(200,"发布成功",orders.getOid());
    }
    @GetMapping("/orders")
    public Result pageList(@RequestParam(name = "currentpage",defaultValue = "1") Integer currentpage,@RequestParam(name = "size",defaultValue = "10") Integer size )
    {
        Page page = new Page(currentpage,size);
        IPage iPage = ordersService.page(page,new QueryWrapper<Orders>().orderByDesc("oid").eq("status",0).or().eq("status",1));
        return Result.success(iPage);
    }
    @GetMapping("/orders/history")
    public Result historyPageList(@RequestParam(name = "currentpage",defaultValue = "1") Integer currentpage,@RequestParam(name = "size",defaultValue = "10") Integer size )
    {
        Page page = new Page(currentpage,size);
        IPage iPage = ordersService.page(page,new QueryWrapper<Orders>().orderByDesc("oid").eq("status",2).or().eq("status",3));
        return Result.success(iPage);
    }

    @GetMapping("/orders/list")
    public Result getList(){
        return Result.success(200,"成功",ordersService.getBaseMapper().selectList(new QueryWrapper<Orders>().eq("status",0)));
    }

    @PostMapping("/orders/accept/{oid}")
    public Result accept(@PathVariable(name = "oid",required = true) Long oid)
    {
        ordersService.acOrder(oid,1);
        return Result.success(200,"交易成功",oid);
    }



    @PostMapping("/orders/reject/{oid}")
    public Result reject(@PathVariable(name = "oid",required = true) Long oid)
    {
        ordersService.acOrder(oid,3);
        return Result.success(200,"交易取消",oid);
    }

    @PostMapping("/orders/success")
    public Result success(@Param("oid") Long oid,@Param("gid") Long gid)
    {
        Good good = goodService.getOne(new QueryWrapper<Good>().eq("gid",gid));
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<>();
        good.setUid(null).setImage(null).setPrice(null).setGname(null).setDescription(null).setGid(null);
        updateWrapper.set("status",2).set("stock",good.getStock()-1).eq("gid",gid);
        goodService.getBaseMapper().update(good,updateWrapper);
        ordersService.acOrder(oid,2);
        return Result.success(200,"交易成功",oid);
    }

}