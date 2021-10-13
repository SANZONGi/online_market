package com.zjgsu.online_market.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(Param.class)
@ConfigurationProperties(prefix = "xjj")
public class Param {
    private String imgFilepath;

    public String getImgFilePath() {
        return imgFilepath;
    }

    public void setImgFilePath(String imgFilepath) {
        this.imgFilepath = imgFilepath;
    }
}
