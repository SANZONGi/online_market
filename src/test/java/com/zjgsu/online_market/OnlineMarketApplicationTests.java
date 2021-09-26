package com.zjgsu.online_market;
import com.zjgsu.online_market.controller.GoodController;
import org.junit.runner.RunWith;
import com.zjgsu.online_market.entity.MyParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Autowired
    private GoodController goodController;
    @Test
    void contextLoads() {
        System.out.println(goodController.unFrozenGood(35L));
    }

}
