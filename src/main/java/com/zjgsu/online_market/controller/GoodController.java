package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.javaws.IconUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.zjgsu.online_market.common.lang.BASE64DecodedMultipartFile;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.MyParam;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.service.impl.GoodServiceImpl;
import com.zjgsu.online_market.service.impl.OrdersServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    private GoodServiceImpl goodService;

    @Autowired
    private OrdersServiceImpl ordersService;

    @GetMapping("/home")
    public Result list() {
        return Result.success( goodService.getBaseMapper().selectList(new QueryWrapper<Good>().ne("status", 2)));
    }

    @PostMapping("/publishGood")
    public Result publishGood(@Param("uid") Long uid, @Param("gname") String gname, @Param("description") String description, @Param("price") Double price, @Param("stock") Integer stock, @Param("image") String image) {
        if (price < 0 || stock < 0)
        {
            return Result.fail(301,"输入格式错误",null);
        }
        String imgname = UUID.randomUUID() + ".png";
        String realpath = null;
        String path = null;
        if (File.separator.equals("/")) {
            realpath = "/home/ubuntu/imgs/" + imgname;
            path = "http://121.5.210.93:8081/static/" + imgname;
//            System.out.println("linux");
        } else {
            realpath = "D:/图片/" + imgname;
            path = "http://localhost:8081/static/" + imgname;
//            System.out.println("other");
        }
        MultipartFile file = BASE64DecodedMultipartFile.base64ToMultipart(image);
        System.out.println(realpath);
        try {
            if (file != null) {
                file.transferTo(new File(realpath));
                goodService.publishGood(uid, gname, description, price, stock, path, 0);
            }
        } catch (IOException e) {
            return Result.fail(605, e.toString(), null);
        }
        return Result.success(null);
    }

    @GetMapping("/good/{gid}")
    public Result goodByid(@PathVariable(name = "gid") Long gid) {
        Good good = goodService.getBaseMapper().selectOne(new QueryWrapper<Good>().eq("gid", gid));
        if (good == null) {
            return Result.fail(601,"商品不存在",null);
        }
        return Result.success(good);
    }

    @GetMapping("/good/alive")
    public Result checkGoodByStatus() {
        return Result.success(goodService.getBaseMapper().selectList(new QueryWrapper<Good>().ne("status", 2)).isEmpty());
    }

    @GetMapping("good/frozen")
    public Result goodInSell() {
        if (ordersService.getBaseMapper().selectList(new QueryWrapper<Orders>().eq("status",1)).isEmpty())
        {
            return Result.success(null);
        }
        return Result.success(goodService.getBaseMapper().selectList(new QueryWrapper<Good>().eq("status", 1).or().eq("status",0)));
    }

    @PostMapping("good/frozen/{gid}")
    public Result frozenGood(@PathVariable("gid") Long gid) {
        Good good = goodService.getOne(new QueryWrapper<Good>().eq("gid",gid));
        if (good == null){
            return Result.fail(601,"商品不存在",null);
        }
        if (good.getStatus() == 1) {
            return Result.fail(604,"商品冻结中",null);
        }
        if (good.getStatus() == 2) {
            return Result.fail(603,"商品下架中",null);
        }
        good.setUid(null).setGname(null).setDescription(null).setImage(null).setPrice(null).setStock(null);
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<Good>();
        updateWrapper.set("status",1).eq("gid",gid);
        goodService.getBaseMapper().update(good,updateWrapper);
        return Result.success(gid);
    }

    @PostMapping("good/unfrozen/{gid}")
    public Result unFrozenGood(@PathVariable("gid") Long gid) {
        Good good = goodService.getOne(new QueryWrapper<Good>().eq("gid",gid));
        if (good == null){
            return Result.fail(601,"商品不存在",null);
        }
        if (good.getStatus() == 0) {
            return Result.fail(602,"商品解冻中",null);
        }
        if (good.getStatus() == 2) {
            return Result.fail(603,"商品下架中",null);
        }
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<>();
        good.setUid(null).setGname(null).setDescription(null).setImage(null).setPrice(null).setStock(null);
        updateWrapper.set("status",0).eq("gid",gid);
        goodService.getBaseMapper().update(good,updateWrapper);
        return Result.success(gid);
    }

    @GetMapping("/good/listofhis")
    public Result listOfHis(@RequestParam(name = "currentpage", defaultValue = "1") Long currentpage, @RequestParam("size") Integer size) {
        Page page = new Page(currentpage, size);
        IPage iPage = goodService.page(page, new QueryWrapper<Good>().orderByDesc("gid").eq("status", 2));
        return Result.success(200, "查询成功", iPage);
    }

}
