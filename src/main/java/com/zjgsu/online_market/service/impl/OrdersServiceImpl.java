package com.zjgsu.online_market.service.impl;

import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.mapper.OrdersMapper;
import com.zjgsu.online_market.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    public void acOrder(@RequestParam("oid") Long oid,@RequestParam("status") Integer status)
    {
        ordersMapper.acOrder(oid,status);
    }

}
