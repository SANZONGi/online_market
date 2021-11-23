package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.common.dto.PageDto;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.mapper.GoodMapper;
import com.zjgsu.online_market.mapper.OrdersMapper;
import com.zjgsu.online_market.service.IOrdersService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    public Integer setOrderStatusById(Long oid, Integer status) {
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<Orders>().eq("oid", oid).set("status", status);
        return ordersMapper.update(null, updateWrapper);
    }

    public List<HashMap<String, String>> getOrdersAndUsersWithStatus(Integer status) {
        return ordersMapper.getOrdersAndUsersWithStatus(status);
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

    public List<HashMap<String, String>> getOrdersAndUsersPageWithStatus(Long current, Integer size, Long uid,List<Integer> status) {
        if (current - 1 < 0 || size < 0) {
            throw new RuntimeException("分页参数错误");
        }
        current = (current - 1) * size;
        List<HashMap<String, String>> res = ordersMapper.getOrdersAndUsersPageWithStatus(current, size, uid, status);
        return res;
    }

    public IPage getOrderPage(Long currentpage, Integer size) {
        Page page = new Page(currentpage, size);
        IPage iPage = ordersMapper.selectPage(page, new QueryWrapper<Orders>().orderByDesc("oid")
                .eq("status", ORDER_WAITING).or().eq("status", ORDER_EXCHANGING));
        return iPage;
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
        //商品有问题
        if (good == null || good.getStock() <= 0 || good.getStatus() == 2) return 1;
        if (goodMapper.updateStatus(ORDER_SUCCESS, gid) == 0 || setOrderStatusById(oid, ORDER_SUCCESS) == 0) {
            return 2;
        }
        redisTemplate.delete(String.valueOf(gid));
        return 0;
    }
}
