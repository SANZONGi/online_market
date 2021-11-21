package com.zjgsu.online_market;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zjgsu.online_market.common.utils.EncypterUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;




@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Value("${xjj.secret}")
    private String secret;
    @Test
    void contextLoads() {
        System.out.println(secret);
//        System.out.println(new EncypterUtil().Encrypt("111"));
    }
}
