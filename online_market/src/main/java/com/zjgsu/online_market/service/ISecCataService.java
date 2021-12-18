package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.SecCata;

import java.sql.SQLException;
import java.util.List;

public interface ISecCataService extends IService<SecCata> {
    List<SecCata> getAll();

    List<SecCata> getAllInPri(Integer pri);

    /**
     * 插入一个二级列表
     * @param secCataList
     */
    void addSecList(Integer pri,List<String> secCataList) throws SQLException;

    /**
     * 插入一个二级项
     * @param pri
     * @param name
     */
    void addSec(Integer pri,String name) throws SQLException;

    /**
     * 删除一个二级项
     * @param pri
     * @param sec
     * @return
     */
    Integer deleteSec(Integer pri,Integer sec);
}
