package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.SecCata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SecCataMapper extends BaseMapper<SecCata> {
    @Select("select * from sec_cata")
    List<SecCata> getAll();
}
