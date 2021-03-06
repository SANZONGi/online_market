package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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

    @Select("<script>" +
            "SELECT orders.*,users.username,users.address,users.phone\n" +
            "        FROM orders LEFT JOIN users on users.uid = orders.uid\n" +
            "        where orders.status in\n" +
            "        <foreach collection=\"status\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "            #{item}\n" +
            "        </foreach>\n" +
            "        <if test='uid != null'>\n" +
            "            and orders.uid = #{uid}\n" +
            "        </if>\n" +
            "        <if test='current!= null and size != null'>\n" +
            "            LIMIT #{current}, #{size}\n" +
            "        </if>"+
            "</script>")
    List<HashMap<String, Object>> getOrdersAndUsersPageWithStatus(Integer current, Integer size, Long uid, List<Integer> status);


    @Select("<script>" +
            "SELECT count(1)\n" +
            "        FROM orders LEFT JOIN users on users.uid = orders.uid\n" +
            "        where orders.status in\n" +
            "        <foreach collection=\"status\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "            #{item}\n" +
            "        </foreach>\n" +
            "        <if test='uid != null'>\n" +
            "            and orders.uid = #{uid}\n" +
            "        </if>\n" +
            "</script>")
    Integer countOrdersAndUsersPageWithStatus(Long uid, List<Integer> status);
}
