package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.entity.PriCata;
import com.zjgsu.online_market.mapper.PriCataMapper;
import com.zjgsu.online_market.service.IPriCataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriCataServiceImpl extends ServiceImpl<PriCataMapper, PriCata> implements IPriCataService {

    @Autowired
    private PriCataMapper priCataMapper;


    @Override
    public List<PriCata> getAll() {
        return priCataMapper.getAll();
    }
}
