package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Orders;
import com.zjgsu.online_market.mapper.GoodMapper;
import com.zjgsu.online_market.mapper.OrdersMapper;
import com.zjgsu.online_market.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    private final static Integer ORDER_WAITING = 0;
    private final static Integer ORDER_EXCHANGING = 1;
    private final static Integer ORDER_SUCCESS = 2;
    private final static Integer ORDER_FAIL = 3;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    public void setOrderStatusById(Long oid, Integer status) {
        Orders orders = new Orders().setStatus(status);
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<Orders>().eq("oid", oid);
        ordersMapper.update(orders, updateWrapper);
    }

    public Result insertOrders(Orders orders) {
        LocalDateTime now = LocalDateTime.now();
        orders.setDate(now).setOid(null);
        ordersMapper.insert(orders);
        return Result.success(null);
    }

    /**
     * 通过id设置订单成功
     * **/
    @Transactional
    public Boolean acceptOrder(Long oid) {
        /*获取order的时候加行锁，防止下面更新之前被并发线程更改*/
        Orders orders = ordersMapper.getOrderByOidForUpdate(oid);
        if (orders == null || orders.getStatus().equals(ORDER_EXCHANGING)) {
            return false;
        }
        setOrderStatusById(oid,ORDER_EXCHANGING);
        return true;
    }

    public IPage getHistoryListPage(Integer currentpage,Integer size) {
        Page page = new Page(currentpage,size);
        IPage iPage = ordersMapper.selectPage(page,new QueryWrapper<Orders>().orderByDesc("oid")
                .eq("status",ORDER_SUCCESS).or().eq("status",ORDER_FAIL));
        return iPage;
    }

    /**
     * 通过id设置订单失败
     * **/
    @Transactional
    public Boolean rejectById(Long oid) {
        Orders orders = ordersMapper.selectOne(new QueryWrapper<Orders>().eq("oid",oid));
        if (orders == null)
        {
            return false;
        }
        setOrderStatusById(oid,ORDER_FAIL);
        return true;
    }

    public IPage getOrderPage(Integer currentpage,Integer size) {
        Page page = new Page(currentpage,size);
        IPage iPage = ordersMapper.selectPage(page,new QueryWrapper<Orders>().orderByDesc("oid")
                .eq("status",ORDER_WAITING).or().eq("status",ORDER_EXCHANGING));
        return iPage;
    }

    @Transactional
    public Integer successById(Long oid,Long gid) {
        Good good = goodMapper.selectByGidForUpdate(gid);
        if (good.getStock() <= 0)
            return 1;
        if (good.getStatus() == 2)
            return 2;
        UpdateWrapper<Good> updateWrapper = new UpdateWrapper<Good>().eq("gid",gid);
        Good new_good = new Good().setStatus(ORDER_SUCCESS).setStock(good.getStock()-1);
        goodMapper.update(new_good,updateWrapper);
        setOrderStatusById(oid,ORDER_SUCCESS);
        return 3;
    }
}
