package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.mapper.GoodMapper;
import com.zjgsu.online_market.service.IGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Service
public class GoodServiceImpl  extends ServiceImpl<GoodMapper,Good> implements IGoodService{
    @Autowired
    private GoodMapper goodMapper;

//    public List<Good> selectAll(){
//        return goodMapper.selectList(new QueryWrapper<Good>().ne("staus",2));
//    }

    public void publishGood(@Param("uid") Long uid, @Param("gname") String gname, @Param("description") String description, @Param("price") BigDecimal price, @Param("stock") Integer stock, @Param("image") String image,@Param("status") Integer status)
    {
        goodMapper.publishGood(uid,gname,description,price,stock,image,status);
    }

    public void updateGoodStatus_sell(@Param("gid") Long gid,@Param("stock") Integer stock,@Param("status") Integer status){
        goodMapper.updateGoodStatus_sell(gid,stock,status);
    };

}
