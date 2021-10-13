package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.service.impl.GoodServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/home")
    public Result list() {
        return Result.success( goodService.getBaseMapper().selectList(new QueryWrapper<Good>().ne("status", 2)));
    }

    @PostMapping("/publishGood")
    public Result publishGood(@Param("uid") Long uid, @Param("gname") String gname, @Param("description") String description, @Param("price") Double price, @Param("stock") Integer stock, @Param("image") String image) {
        if (uid == null || gname == null || description == null || price == null || stock == null || image == null)
            return Result.fail("发布商品有空对象",603);
        if (goodService.publsh(uid,gname,description,price,stock,image))
            return Result.success("发布成功");
        else
            return Result.fail("发布失败");
    }

    @GetMapping("/good/{gid}")
    public Result goodByid(@PathVariable(name = "gid") Long gid) {
        Good good = goodService.getBaseMapper().selectOne(new QueryWrapper<Good>().eq("gid", gid));
        if (good == null) {
            return Result.fail("商品不存在");
        }
        return Result.success(good);
    }

    @GetMapping("/good/alive")
    public Result checkGoodByStatus() {
        return Result.success(goodService.getBaseMapper().selectCount(new QueryWrapper<Good>().ne("status", 2)) == 0);      //若存在商品的就返回false
    }

    @GetMapping("good/frozen")
    public Result goodInSell() {
       return Result.success(goodService.getFrozenGood());
    }

    @PostMapping("good/frozen/{gid}")
    public Result frozenGood(@PathVariable("gid") Long gid) {
        Good good = goodService.getOne(new QueryWrapper<Good>().eq("gid",gid));
        if (good == null){
            return Result.fail(406,"商品不存在",null);
        }
        if (good.getStatus() == 1) {
            return Result.fail(406,"商品冻结中",null);
        }
        if (good.getStatus() == 2) {
            return Result.fail(406,"商品下架中",null);
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
            return Result.fail(406,"商品不存在",null);
        }
        if (good.getStatus() == 0) {
            return Result.fail(406,"商品解冻中",null);
        }
        if (good.getStatus() == 2) {
            return Result.fail(406,"商品下架中",null);
        }
        Good new_good = new Good();
        new_good.setStatus(0);
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("gid",gid);
        goodService.getBaseMapper().update(new_good,updateWrapper);
        return Result.success(gid);
    }

    @GetMapping("/good/listofhis")
    public Result listOfHis(@RequestParam(name = "currentpage", defaultValue = "1") Long currentpage, @RequestParam("size") Integer size) {
        Page page = new Page(currentpage, size);
        IPage iPage = goodService.page(page, new QueryWrapper<Good>().orderByDesc("gid").eq("status", 2));
        return Result.success(200, "查询成功", iPage);
    }

}
