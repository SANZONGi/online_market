package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.common.lang.BASE64DecodedMultipartFile;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Img;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.mapper.GoodMapper;
import com.zjgsu.online_market.mapper.ImgMapper;
import com.zjgsu.online_market.mapper.OrdersMapper;
import com.zjgsu.online_market.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 * 0 交易
 * 1 冻结
 * 2 下架
 */
@Service
@PropertySource("classpath:application.yml")
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {
    private final static Integer GOOD_SALE = 0;
    private final static Integer GOOD_FROZEN = 1;
    private final static Integer GOOD_SELLOUT = 2;

    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ImgMapper imgMapper;
    @Value("${xjj.imgfilepath}")
    private String LocalIngPath;

    @Autowired
    private OrdersMapper ordersMapper;

    public Good getGoodById(Long id) {
        Good good = (Good) redisTemplate.opsForValue().get(String.valueOf(id));
        if (good == null) {
            good = goodMapper.selectById(id);
            redisTemplate.opsForValue().set(String.valueOf(id), good);
        }
        return good;

    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<Good> getFrozenGood() {
        if (ordersMapper.selectCount(new QueryWrapper<Orders>().eq("status", GOOD_FROZEN)) == 0) { //没有订单激活
            if (goodMapper.selectCount(new QueryWrapper<Good>().eq("status", GOOD_FROZEN)) != 0) //有商品还在冻结
            {
                return goodMapper.selectList(new QueryWrapper<Good>().eq("status", GOOD_FROZEN));
            } else {
                return null;
            }
        }
        return goodMapper.selectList(new QueryWrapper<Good>().eq("status", GOOD_FROZEN).or().eq("status", GOOD_SALE));
    }


    @Transactional
    public Integer publish(Good good, List<MultipartFile> files) throws IOException {
        String realpath;
        String path;
        if (File.separator.equals("/")) {
            realpath = "/home/ubuntu/imgs/";
            path = "http://121.5.210.93:8081/static/";
        } else {
            realpath = LocalIngPath;
            path = "http://localhost:8081/static/";
        }
        for (MultipartFile file : files) {
            if (file == null) {
                return 2;
            }
        }
        goodMapper.insertSelectiveHasKey(good);
        System.out.println(good);
        for (MultipartFile file : files) {
            String origin = file.getOriginalFilename();
            if (origin == null) origin = ".jpg";
            String fileName = UUID.randomUUID().toString()+origin.substring(origin.lastIndexOf('.'));
            file.transferTo(new File(realpath+fileName));
            imgMapper.insert(new Img(good.getGid(),path+fileName));
        }

        return 0;
    }

    public List<Good> getGoodByPri(@NotNull Integer pri)
    {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<Good>().eq("pri_cata",pri);
        return goodMapper.selectList(queryWrapper);
    }

    public List<Good> getGoodBySec(@NotNull Integer pri,@NotNull Integer sec)
    {
        QueryWrapper<Good> queryWrapper = new QueryWrapper<Good>().eq("pri_cata",pri).eq("sec_cata",sec);
        return goodMapper.selectList(queryWrapper);
    }


    @Transactional
    public Result frozeGoodById(Long gid) {
        Good good = goodMapper.selectByGidForUpdate(gid);
        if (good.getStatus().equals(GOOD_FROZEN))
            return Result.fail(406,"商品冻结中",null);

        if (good.getStatus().equals(GOOD_SELLOUT))
            return Result.fail(406,"商品下架中",null);

        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<Good>().eq("gid",gid).set("status",GOOD_FROZEN);
        goodMapper.update(null,updateWrapper);
        redisTemplate.delete(String.valueOf(gid));
        return Result.success(gid);
    }

    @Transactional
    public Result unFrozenGood(Long gid) {
        Good good = goodMapper.selectByGidForUpdate(gid);
        if (good.getStatus() == 0)
            return Result.fail(406,"商品解冻中",null);
        if (good.getStatus() == 2)
            return Result.fail(406,"商品下架中",null);
        good.setStatus(GOOD_SALE);
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<Good>().eq("gid",gid);
        goodMapper.update(good,updateWrapper);
        redisTemplate.delete(String.valueOf(gid));
        return Result.success(gid);
    }
}
