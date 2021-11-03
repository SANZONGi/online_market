package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImgMapper extends BaseMapper<Img> {
    @Select("select img_url from img where git = #{gid}")
    List<String> getUrlByGid(Long gid);
}
