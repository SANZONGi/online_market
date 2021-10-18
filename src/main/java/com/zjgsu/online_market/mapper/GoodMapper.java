package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */

@Mapper
public interface GoodMapper extends BaseMapper<Good>{
    @Select("select * from good where gid = #{gid} for update")
    Good selectByGidForUpdate(Long gid);
}
