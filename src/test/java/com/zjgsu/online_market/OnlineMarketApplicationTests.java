package com.zjgsu.online_market;

import com.zjgsu.online_market.common.utils.EncypterUtil;
import com.zjgsu.online_market.service.impl.OrdersServiceImpl;
import com.zjgsu.online_market.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest

class OnlineMarketApplicationTests {
    @Autowired
    private EncypterUtil encypterUtil;
    //    private Param xjj;
    @Autowired
    private OrdersServiceImpl ordersService;

    @Test
    void contextLoads() {
    }
}
