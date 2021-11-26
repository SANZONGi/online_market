package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.entity.PriCata;
import com.zjgsu.online_market.entity.SecCata;
import com.zjgsu.online_market.mapper.PriCataMapper;
import com.zjgsu.online_market.mapper.SecCataMapper;
import com.zjgsu.online_market.service.IPriCataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriCataServiceImpl extends ServiceImpl<PriCataMapper, PriCata> implements IPriCataService {

    @Autowired
    private PriCataMapper priCataMapper;

    @Autowired
    private SecCataMapper secCataMapper;

    @Override
    public List<PriCata> getAll() {
        return priCataMapper.getAll();
    }

    @Override
    public Integer insertHasKey(PriCata pri) {
        priCataMapper.insertHasKey(pri);
        return pri.getId();
    }

    @Override
    public Integer insertPriAndSec(String priName, String SecName) {
        PriCata priCata = new PriCata().setName(priName);
        int priId;
        try{
            priId = insertHasKey(priCata);
        }catch (Exception e)
        {
            log.error("错误原因"+e.getCause().getMessage());
            return 1;
        }
        try{
            secCataMapper.insert(new SecCata().setPriId(priId).setName(SecName));
        }catch (Exception e) {
            log.error("错误原因" + e.getCause().getMessage());
            return 2;
        }
        return 0;
    }


}
