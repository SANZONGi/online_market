package com.zjgsu.online_market;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjgsu.online_market.controller.GoodController;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Param;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Autowired
    private Param xjj;
//    private GoodServiceImpl goodService;
//    private GoodController goodController;
    @Test
    void contextLoads() {
        System.out.println(xjj.getImgfilepath());
    }

}
