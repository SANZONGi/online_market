package com.zjgsu.online_market;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zjgsu.online_market.mapper")
@SpringBootApplication
public class OnlineMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketApplication.class, args);
    }
}
