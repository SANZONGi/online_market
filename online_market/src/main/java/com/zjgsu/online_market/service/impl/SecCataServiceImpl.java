package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.entity.SecCata;
import com.zjgsu.online_market.mapper.SecCataMapper;
import com.zjgsu.online_market.service.ISecCataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
@Service
public class SecCataServiceImpl extends ServiceImpl<SecCataMapper, SecCata> implements ISecCataService {
    @Autowired
    private SecCataMapper secCataMapper;

    @Override
    public List<SecCata> getAll() {
        return secCataMapper.getAll();
    }

    @Override
    public List<SecCata> getAllInPri(Integer pri) {
        return secCataMapper.getAllInPri(pri);
    }

    @Transactional(rollbackFor = SQLException.class)
    @Override
    public void addSecList(Integer pri, List<String> secCataList) throws SQLException{
        for (String item : secCataList) {
            addSec(pri, item);
        }
    }

    @Override
    public void addSec(Integer pri, String name) throws SQLException{
        secCataMapper.insert(new SecCata().setPriId(pri).setName(name));
    }

    @Override
    public Integer deleteSec(Integer pri, Integer sec) {
        return secCataMapper.deleteSec(pri,sec);
    }


}
