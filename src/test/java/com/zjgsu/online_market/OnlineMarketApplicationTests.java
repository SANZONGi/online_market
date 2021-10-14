package com.zjgsu.online_market;

import com.zjgsu.online_market.entity.Param;
import com.zjgsu.online_market.entity.Users;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Autowired
    private Param xjj;
    @Autowired
    RedisTemplate redisTemplate;
//    private GoodServiceImpl goodService;
//    private GoodController goodController;
    @Test
    void contextLoads() {
        Users users = new Users().setAddress("11").setUsername("user").setPassword("1").setPhone("111");
        redisTemplate.opsForValue().set("user",  users, 200, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
