package com.zjgsu.online_market;

import com.zjgsu.online_market.common.utils.EncypterUtil;
import com.zjgsu.online_market.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class OnlineMarketApplicationTests {
    @Autowired
    private EncypterUtil encypterUtil;
    //    private Param xjj;
    @Autowired
    private UsersServiceImpl usersService;

    //        RedisTemplate redisTemplate;
//    private GoodController goodController;
    @Test
    void contextLoads() {
//        usersService.insertUser(new Users().setUsername("admin1").setPassword(encypterUtil.Encrypt("1")).setPhone("11111111111").setAddress("address"));
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
//        System.out.println(encypterUtil.Encrypt("1"));
//        usersService.insertUser(new Users().setPhone("").setPassword(encypterUtil.Encrypt("1")).setUsername("xjj").setAddress("1").setPhone("1111111111"));
//        redisTemplate.opsForValue().set("1","1");
//        System.out.println(redisTemplate.opsForValue().get("1"));
        System.out.println(encypterUtil.Decrypt("wEUAsyBgjO1khxzsgCN8RA=="));
    }

    //AzJn93jPAH8G2/2uyC306gdibQqy9Bir/I+64jKfKYpsqCnWEAuwPA==
    //wEUAsyBgjO1khxzsgCN8RA==
}
