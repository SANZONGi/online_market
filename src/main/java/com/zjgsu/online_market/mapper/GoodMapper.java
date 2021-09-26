package com.zjgsu.online_market.mapper;

import com.zjgsu.online_market.entity.Good;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */

@Mapper
public interface GoodMapper extends BaseMapper<Good>{
    @Insert("insert into good(uid,gname,description,price,stock,image,status) values(#{uid},#{gname},#{description},#{price},#{stock},#{image},#{status})")
    void publishGood(@Param("uid") Long uid, @Param("gname") String gname, @Param("description") String description, @Param("price") Double price, @Param("stock") Integer stock, @Param("image") String image,@Param("status") Integer status);
    @Update("update good set status = #{status}, stock = #{stock} where gid = #{gid}")
    void updateGoodStatus_sell(@Param("gid") Long gid,@Param("stock") Integer stock, @Param("status") Integer status);
}
