package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.common.dto.GoodDto;
import com.zjgsu.online_market.entity.Good;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */

@Mapper
@Repository
public interface GoodMapper extends BaseMapper<Good>{
    @Select("select * from good where gid = #{gid} for update")
    Good selectByGidForUpdate(Long gid);

    @Options(useGeneratedKeys = true, keyProperty = "gid", keyColumn = "gid")
    @Insert("insert  into good (uid,gname,price,stock,status,description,pri_cata,sec_cata) values (#{uid},#{gname},#{price},#{stock},#{status},#{description},#{priCata},#{secCata})")
    void insertHasKey(Good good);

    @Update("update good set status = #{status} where gid = #{gid}")
    Integer updateStatus(Integer status,Long gid);

    @Update("update good set stock = stock - 1 where gid = #{gid}")
    Integer decreaseById(@Param("gid") Long gid);

    @Update("update good set stock = stock + 1 where gid = #{gid}")
    Integer increaseById(@Param("gid") Long gid);

    @Select("select good.gid,good.uid,good.gname,good.price,good.pri_cata,good.sec_cata,good.`status`,good.stock,good.description,img_url from good LEFT JOIN img on good.gid = img.gid where good.gid = #{gid} limit 1")
    GoodDto getGoodDto(Long gid);
}
