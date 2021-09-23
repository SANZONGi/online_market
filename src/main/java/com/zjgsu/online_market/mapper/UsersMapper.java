package com.zjgsu.online_market.mapper;

import com.zjgsu.online_market.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface UsersMapper extends BaseMapper<Users>{
    @Select("select * from users where username = #{username}")
    Users checkUserByUsername(@Param("username") String username);

    @Insert("insert into users(username,password,phone,address) values(#{username},#{password},#{phone},#{address})")
    void insertUser(@Param("username") String username,@Param("password") String password,@Param("phone") String phone,@Param("address") String address);
}
