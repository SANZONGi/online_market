package com.zjgsu.online_market.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(MyParam.class)
@ConfigurationProperties(prefix = "xjj")
public class MyParam {
    private String imgfilepath;

    public String getImgfilepath() {
        return imgfilepath;
    }

    public void setImgfilepath(String imgfilepath) {
        this.imgfilepath = imgfilepath;
    }
}
