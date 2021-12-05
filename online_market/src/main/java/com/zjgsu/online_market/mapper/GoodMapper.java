package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.Good;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

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

    @Select("<script>" +
            "select gid,uid,gname,price,pri_cata,sec_cata,`status`,stock,description,img_url\n" +
            "from ( \n" +
            "    select good.gid,good.uid,good.gname,good.price,good.pri_cata,good.sec_cata,good.`status`,good.stock,good.description,img_url,\n" +
            "\t\trow_number() over (partition by good.gid order by good.gid) as group_idx  \n" +
            "    from good LEFT JOIN img on good.gid = img.gid\n" +
            ")s\n" +
            "where group_idx = 1 and `status` in\n" +
            "        <foreach collection=\"status\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "            #{item}\n" +
            "        </foreach>\n" +
            "LIMIT #{current},#{size}" +
            "</script>"
    )
    List<HashMap<String,Object>> getGoodDtoListByStatus(@Param("status") List<Integer> status, Long current, Integer size);

    @Select("<script>" +
            "select count(1)\n" +
            "from ( \n" +
            "    select good.gid,good.uid,good.gname,good.price,good.pri_cata,good.sec_cata,good.`status`,good.stock,good.description,img_url,\n" +
            "\t\trow_number() over (partition by good.gid order by good.gid) as group_idx  \n" +
            "    from good LEFT JOIN img on good.gid = img.gid\n" +
            ")s\n" +
            "where group_idx = 1 and `status` in\n" +
            "        <foreach collection=\"status\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "            #{item}\n" +
            "        </foreach>\n" +
            "</script>"
    )
    Long countGoodDtoListByStatus(@Param("status") List<Integer> status);

    @Select("<script>" +
            "select gid,uid,gname,price,pri_cata,sec_cata,`status`,stock,description,img_url\n" +
            "from ( \n" +
            "    select good.gid,good.uid,good.gname,good.price,good.pri_cata,good.sec_cata,good.`status`,good.stock,good.description,img_url,\n" +
            "\t\trow_number() over (partition by good.gid order by good.gid) as group_idx  \n" +
            "    from good LEFT JOIN img on good.gid = img.gid\n" +
            ")s\n" +
            "where group_idx = 1 and (gname like #{val} or description like #{val})\n" +
            "LIMIT #{current},#{size}"+
            "</script>"
    )
    List<HashMap<String,Object>> getGoodDtoListBySearch(String val, Long current, Integer size);
    @Select("<script>" +
            "select count(1)\n" +
            "from ( \n" +
            "    select good.gid,good.uid,good.gname,good.price,good.pri_cata,good.sec_cata,good.`status`,good.stock,good.description,img_url,\n" +
            "\t\trow_number() over (partition by good.gid order by good.gid) as group_idx  \n" +
            "    from good LEFT JOIN img on good.gid = img.gid\n" +
            ")s\n" +
            "where group_idx = 1 and (gname like #{val} or description like #{val})\n"+
            "</script>")
    Long countGoodDtoListBySearch(String val);

    @Select("<script>" +
            "select gid,uid,gname,price,pri_cata,sec_cata,`status`,stock,description,img_url\n" +
            "from ( \n" +
            "    select good.gid,good.uid,good.gname,good.price,good.pri_cata,good.sec_cata,good.`status`,good.stock,good.description,img_url,\n" +
            "\t\trow_number() over (partition by good.gid order by good.gid) as group_idx  \n" +
            "    from good LEFT JOIN img on good.gid = img.gid\n" +
            ")s\n" +
            "where group_idx = 1" +
            "<if test='pri != null'>\n" +
            "and pri_cata = #{pri}\n" +
            "</if>\n" +
            "<if test='sec != null'>\n" +
            "and sec_cata = #{sec}\n" +
            "</if>\n" +
            "LIMIT #{current},#{size}"+
            "</script>"
    )
    List<HashMap<String,Object>> getGoodDtoListByCata(Integer pri,Integer sec, Long current, Integer size);
    @Select("<script>" +
            "select count(1)\n" +
            "from ( \n" +
            "    select good.gid,good.uid,good.gname,good.price,good.pri_cata,good.sec_cata,good.`status`,good.stock,good.description,img_url,\n" +
            "\t\trow_number() over (partition by good.gid order by good.gid) as group_idx  \n" +
            "    from good LEFT JOIN img on good.gid = img.gid\n" +
            ")s\n" +
            "where group_idx = 1" +
            "<if test='pri != null'>\n" +
            "and pri_cata = #{pri}\n" +
            "</if>\n" +
            "<if test='sec != null'>\n" +
            "and sec_cata = #{sec}\n" +
            "</if>\n" +
            "</script>"
    )
    Long countGoodDtoListByCata(Integer pri,Integer sec);

}
