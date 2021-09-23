package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjgsu.online_market.common.lang.BASE64DecodedMultipartFile;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.MyParam;
import com.zjgsu.online_market.service.impl.GoodServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class GoodController {

    @Autowired
    private GoodServiceImpl goodService;

    @Autowired
    private MyParam xjj;

    @GetMapping("/home")
    public Result list() {
        return Result.success(200, "查询商品列表成功", goodService.getBaseMapper().selectList(new QueryWrapper<Good>().ne("status", 2)));
    }

    @PostMapping("/publishGood")
    @ResponseBody
    public Result publishGood(@Param("uid") Long uid, @Param("gname") String gname, @Param("description") String description, @Param("price") BigDecimal price, @Param("stock") Integer stock, @Param("image") String image) {
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
        MultipartFile file;
        if (image != null)
            file = BASE64DecodedMultipartFile.base64ToMultipart(image);
        else return Result.fail("无图片", null);
        try {
            if (file != null) {
                file.transferTo(new File(realpath));
                goodService.publishGood(uid, gname, description, price, stock, path, 0);
            }
        } catch (IOException e) {
            return Result.fail(203, e.toString(), null);
        }
        return Result.success(200, "发布成功", null);
    }

    @GetMapping("/good/{gid}")
    public Result goodByid(@PathVariable(name = "gid") Long gid) {
        return Result.success(200, "获取成功", goodService.getBaseMapper().selectOne(new QueryWrapper<Good>().eq("gid", gid)));
    }

    @GetMapping("/good/alive")
    public Result checkGoodByStatus() {
        return Result.success(200, "", goodService.getBaseMapper().selectOne(new QueryWrapper<Good>().ne("status", 2)) != null);
    }

    @GetMapping("good/frozen")
    public Result goodInSell() {
        return Result.success(goodService.getBaseMapper().selectList(new QueryWrapper<Good>().eq("status", 1).or().eq("status",0)));
    }

    @PostMapping("good/frozen/{gid}")
    public Result frozenGood(@PathVariable("gid") Long gid)
    {
        Good good = goodService.getOne(new QueryWrapper<Good>().eq("gid",gid));
        good.setUid(null).setGname(null).setDescription(null).setImage(null).setPrice(null).setStock(null);
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<Good>();
        updateWrapper.set("status",1).eq("gid",gid);
        goodService.getBaseMapper().update(good,updateWrapper);
        return Result.success(gid);
    }
    @PostMapping("good/unfrozen/{gid}")
    public Result unFrozenGood(@PathVariable("gid") Long gid)
    {
        Good good = goodService.getOne(new QueryWrapper<Good>().eq("gid",gid));
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
