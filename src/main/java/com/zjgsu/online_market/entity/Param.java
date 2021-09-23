package com.zjgsu.online_market.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(Param.class)
@ConfigurationProperties(prefix = "xjj")
public class Param {
    private String imgfilepath;

    public String getImgfilepath() {
        return imgfilepath;
    }

    public void setImgfilepath(String imgfilepath) {
        this.imgfilepath = imgfilepath;
    }
}
