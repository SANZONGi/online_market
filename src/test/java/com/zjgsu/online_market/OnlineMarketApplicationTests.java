package com.zjgsu.online_market;

import com.zjgsu.online_market.entity.Param;
import com.zjgsu.online_market.mapper.GoodMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Autowired
    private Param xjj;
    @Autowired
//    private OrdersMapper ordersMapper;
//    RedisTemplate redisTemplate;
    private GoodMapper goodService;
//    private GoodController goodController;
    @Test
    void contextLoads() {
        System.out.println(goodService.selectById(1));
    }

}
