package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.entity.Page;
import com.zjgsu.online_market.mapper.GoodMapper;
import com.zjgsu.online_market.mapper.OrdersMapper;
import com.zjgsu.online_market.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    public final static Integer ORDER_WAITING = 0;
    public final static Integer ORDER_EXCHANGING = 1;
    public final static Integer ORDER_SUCCESS = 2;
    public final static Integer ORDER_FAIL = 3;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrdersMapper ordersMapper;

    public List<Integer> getAllStatus() {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ORDER_WAITING);
        statusList.add(ORDER_FAIL);
        statusList.add(ORDER_EXCHANGING);
        statusList.add(ORDER_SUCCESS);
        return statusList;
    }

    public Integer setOrderStatusById(Long oid, Integer status) {
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<Orders>().eq("oid", oid).set("status", status);
        return ordersMapper.update(null, updateWrapper);
    }

    public Integer insertOrders(Orders orders) {
        LocalDateTime now = LocalDateTime.now();
        orders.setDate(now).setOid(null);
        try {
            ordersMapper.insert(orders);
        } catch (Exception e) {
            log.error(e.getMessage());
            return 1;
        }
        return 0;
    }

    public Page getOrdersAndUsersPageWithStatus(Long current, Integer size, Long uid, List<Integer> status) {
        Page page = new Page();
        page.setTotal(ordersMapper.countOrdersAndUsersPageWithStatus(uid,status));
        if (current != null && size != null) {
            if (current - 1 < 0 || size < 0) throw new  RuntimeException("分页参数错误");
            page.setCurrent(current);
            page.setSize(size);
            current = (current - 1) * size;
        }else {
            page.setCurrent(1L);
            page.setSize((int) (page.getTotal()+1));
        }
        page.setData(ordersMapper.getOrdersAndUsersPageWithStatus(current, size, uid, status));
        return page;
    }

    @Transactional
    public Integer acceptOrder(Long oid, Long gid) {
        /*获取order的时候加行锁，防止下面更新之前被并发线程更改*/
        Orders orders = ordersMapper.getOrderByOidForUpdate(oid);
        Good good = goodMapper.selectById(gid);
        if (good == null || good.getStock() <= 0) {
            return 1;
        }
        if (orders == null || orders.getStatus().equals(ORDER_EXCHANGING)) {
            return 2;
        }
        if (setOrderStatusById(oid, ORDER_EXCHANGING) == 0) {
            return 3;
        }
        //匹配0条更新失败
        if (goodMapper.decreaseById(gid) == 0) {
            return 4;
        }
        return 0;
    }

    @Transactional
    public Integer rejectById(Long oid, Long gid) {
        if (goodMapper.increaseById(gid) == 0) {
            return 1;
        }
        if (setOrderStatusById(oid, ORDER_FAIL) == 0) {
            return 2;
        }
        return 0;
    }

    @Transactional
    public Integer successById(Long oid, Long gid) {
        Good good = goodMapper.selectByGidForUpdate(gid);
        Orders orders = ordersMapper.getOrderByOidForUpdate(oid);
        //商品有问题
        if (good == null || good.getStock() <= 0 || good.getStatus() == 2) return 1;
        if (orders == null || orders.getStatus() == 2 || orders.getStatus() == 3) return 3;
        good.setStock(good.getStock() - 1);
        if (good.getStock() == 0) good.setStatus(GoodServiceImpl.GOOD_SELLOUT);
        if (goodMapper.updateById(good) == 0 || setOrderStatusById(oid, ORDER_SUCCESS) == 0) {
            return 2;
        }
        redisTemplate.delete(String.valueOf(gid));
        return 0;
    }
}
