package com.zjgsu.online_market;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjgsu.online_market.controller.GoodController;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.service.impl.GoodServiceImpl;
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
    private GoodServiceImpl goodService;
//    private GoodController goodController;
    @Test
    void contextLoads() {
        Wrapper<Good> wrapper = new QueryWrapper<Good>().select("DISTINCT uid").eq("status",2);
        System.out.println(goodService.getBaseMapper().selectCount(wrapper));
    }

}
