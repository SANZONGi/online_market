package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zjgsu.online_market.common.lang.BASE64DecodedMultipartFile;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.entity.Param;
import com.zjgsu.online_market.mapper.GoodMapper;
import com.zjgsu.online_market.mapper.OrdersMapper;
import com.zjgsu.online_market.service.IGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
 *  0 交易
 *  1 冻结
 *  2 下架
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private Param xjj;

    @Autowired
    private OrdersMapper ordersMapper;


    public List<Good> getFrozenGood() {
        if (ordersMapper.selectCount(new QueryWrapper<Orders>().eq("status", 1)) == 0) {
            return null;
        }
        return goodMapper.selectList(new QueryWrapper<Good>().eq("status", 1).or().eq("status", 0));
    }

    public void publishGood(Long uid, String gname, String description, Double price, Integer stock, String path, Integer status) {
        Good good = new Good();
        good.setUid(uid).setGname(gname).setDescription(description).setPrice(price).setStock(stock).setImage(path).setStatus(status);
        goodMapper.insert(good);
    }

    public Boolean publsh(Long uid, String gname, String description, Double price, Integer stock, String image) {
        if (price < 0 || stock < 0 || image == null)
        {
            return false;
        }
        String imgname = UUID.randomUUID() + ".png";
        String realpath = null;
        String path = null;
        if (File.separator.equals("/")) {
            realpath = "/home/ubuntu/imgs/" + imgname;
            path = "http://121.5.210.93:8081/static/" + imgname;
//            System.out.println("linux");
        } else {
            realpath = xjj.getImgfilepath() + imgname;
            path = "http://localhost:8081/static/" + imgname;
//            System.out.println("other");
        }
        MultipartFile file = BASE64DecodedMultipartFile.base64ToMultipart(image);
        try {
            if (file != null) {
                file.transferTo(new File(realpath));
                publishGood(uid, gname, description, price, stock, path, 0);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
