package com.zjgsu.online_market;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.zjgsu.online_market.mapper")
@ComponentScan(basePackages = "com.zjgsu.online_market.interceptor")
@SpringBootApplication
public class OnlineMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketApplication.class, args);
    }
}
