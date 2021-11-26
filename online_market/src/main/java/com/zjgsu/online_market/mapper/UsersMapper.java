package com.zjgsu.online_market.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjgsu.online_market.entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

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
public interface UsersMapper extends BaseMapper<Users>{
    @Select("select * from users where username = #{username}")
    Users getUserByUsername(@Param("username") String username);

    @Select("select * from users")
    List<Users> getAllUsers();

    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    @Insert("insert  into users (uid,username,password,phone,address) values (#{uid},#{username},#{password},#{phone},#{address})")
    void insertHasKey(Users users);
}
