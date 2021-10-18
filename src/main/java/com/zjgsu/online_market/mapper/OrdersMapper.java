package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("select * from orders where oid = #{oid} for update")
    Orders getOrderByOidForUpdate(@Param("oid") Long oid);
}
