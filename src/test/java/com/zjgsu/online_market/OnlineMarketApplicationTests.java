package com.zjgsu.online_market;

import com.zjgsu.online_market.mapper.GoodMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Value("${xjj.imgfilepath}")
    private String path;
//    private Param xjj;
    @Autowired
//    private OrdersMapper ordersMapper;
//        RedisTemplate redisTemplate;
    private GoodMapper goodService;
//    private GoodController goodController;
    @Test
    void contextLoads() {
//        DecodedJWT tokenBody = JwtUtils.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiI1IiwiZXhwIjoxNjM1MTYzNzcxLCJ1c2VybmFtZSI6InhqaiJ9.sxPu_oz7njw8pFMiz96CmsNCjNrYOLiqGVwlb5RJgcM");
//        保证是本用户在网页上操作，除非既泄露了密码又泄露了token
//        System.out.println(tokenBody.getClaim("uid").asString());
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        try {
//            Connection connection = hikariDataSource.getConnection();
//            connection.
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        System.out.println(goodService.selectById(47));
//        redisTemplate.opsForValue().set("1","1");
//        System.out.println(redisTemplate.opsForValue().get("1"));
    }

}
