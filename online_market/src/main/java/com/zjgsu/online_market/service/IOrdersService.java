package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.entity.Page;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
public interface IOrdersService extends IService<Orders> {
    Integer setOrderStatusById(Long oid, Integer status);

    Integer insertOrders(Orders orders);

    Page getOrdersAndUsersPageWithStatus(@Min(1) Integer current, @Min(1) Integer size, Long uid, List<Integer> status);

    Integer successById(Long oid, Long gid);

    Integer rejectById(Long oid, Long gid);

    Integer acceptOrder(Long oid,Long gid);

    List<Integer> getAllStatus();
}
