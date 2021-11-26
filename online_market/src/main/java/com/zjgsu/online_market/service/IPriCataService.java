package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.PriCata;

import java.util.List;

public interface IPriCataService extends IService<PriCata> {
    List<PriCata> getAll();

    Integer insertHasKey(PriCata pri);

    Integer insertPriAndSec(String priName,String SecName);

}
