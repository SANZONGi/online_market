package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.SecCata;

import java.util.List;

public interface ISecCataService extends IService<SecCata> {
    List<SecCata> getAll();

    List<SecCata> getAllInPri(Integer pri);
}
