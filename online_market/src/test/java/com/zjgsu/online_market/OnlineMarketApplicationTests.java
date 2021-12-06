package com.zjgsu.online_market;

import com.zjgsu.online_market.mapper.OrdersMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Autowired
    OrdersMapper ordersMapper;
    @Test
    void contextLoads() {
    }
}
