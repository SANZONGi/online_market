package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Orders;

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

    Result insertOrders(Orders orders);

    IPage getHistoryListPage(Long currentpage, Integer size);

    IPage getOrderPage(Long currentpage, Integer size);

    Integer successById(Long oid, Long gid);

    Boolean rejectById(Long oid);

    Boolean acceptOrder(Long oid);
}
