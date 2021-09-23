package com.zjgsu.online_market.mapper;

import com.zjgsu.online_market.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    @Update("update orders set status = #{status} WHERE oid = #{oid}")
    public void acOrder(@Param("oid") Long oid, @Param("status") Integer status);
}
