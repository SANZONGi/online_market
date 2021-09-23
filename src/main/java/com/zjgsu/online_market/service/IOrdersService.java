package com.zjgsu.online_market.service;

import com.zjgsu.online_market.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
public interface IOrdersService extends IService<Orders> {
    public void acOrder(@Param("oid") Long oid, @Param("status") Integer status);
}
