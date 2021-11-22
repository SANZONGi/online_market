package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.common.dto.PageDto;
import com.zjgsu.online_market.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Mapper
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
    @Select("select * from orders where oid = #{oid} for update")
    Orders getOrderByOidForUpdate(@Param("oid") Long oid);

    @Select("SELECT orders.*,users.username,users.address,users.phone FROM orders LEFT JOIN users on users.uid = orders.uid where orders.`status` = #{status}")
    List<HashMap<String, String>> getOrdersAndUsersWithStatus(Integer status);

    @Select("<script>" +
            "SELECT orders.*,users.username,users.address,users.phone " +
            "FROM orders LEFT JOIN users on users.uid = orders.uid " +
            "where orders.`status` = #{status}" +
            "<if test='uid != null'>" +
            "and orders.uid = #{uid}" +
            "</if>" +
            "<if test='current!= null and size != null'>" +
            "LIMIT #{current}, #{size}" +
            "</if>" +
            "</script>")
    List<HashMap<String, String>> getHistoryOrdersAndUsersPageWithStatus(Long current,Integer size, Long uid, Integer status);
}
