package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.PriCata;

import java.util.List;

/**
 * @author SANZONG
 */
public interface IPriCataService extends IService<PriCata> {

    /**
     * 获取全部一级目录
     * @return
     */
    List<PriCata> getAll();

    /**
     * 插入并返回在数据库中的id
     * @param pri
     * @return
     */
    Integer insertHasKey(PriCata pri);

    /**
     * 插入一级和二级目录
     * @param priName
     * @param SecName
     * @return
     */
    Integer insertPriAndSec(String priName,String SecName);

}
