package com.zjgsu.online_market.service;

import com.zjgsu.online_market.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.mapper.GoodMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */

public interface IGoodService extends IService<Good>{
    void publishGood(@Param("uid") Long uid, @Param("gname") String gname, @Param("description") String description, @Param("price") BigDecimal price, @Param("stock") Integer stock, @Param("image") String image,@Param("status") Integer status);
    void updateGoodStatus_sell(@Param("gid") Long gid,@Param("stock") Integer stock,@Param("status") Integer status);
}
