package com.zjgsu.online_market;

import com.zjgsu.online_market.entity.MyParam;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({MyParam.class})
@MapperScan("com.zjgsu.online_market.mapper")
@SpringBootApplication
public class OnlineMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineMarketApplication.class, args);
    }
}