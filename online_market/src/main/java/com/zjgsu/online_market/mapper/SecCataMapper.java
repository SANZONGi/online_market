package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.SecCata;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author SANZONG
 */
@Mapper
public interface SecCataMapper extends BaseMapper<SecCata> {
    /**
     * 所有二级项
     * @return List
     */
    @Select("select * from sec_cata")
    List<SecCata> getAll();

    /**
     * 获取一级项pri下所有二级项
     * @param pri
     * @return List
     */
    @Select("select * from sec_cata where pri_id = #{pri}")
    List<SecCata> getAllInPri(Integer pri);

    /**
     * 删除一个二级项
     * @param pri
     * @param sec
     * @return none
     */
    @Delete("delete from sec_cata where pri_id = #{pri} and id = #{sec}")
    int deleteSec(Integer pri,Integer sec);
}
