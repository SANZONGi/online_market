package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Users;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
public interface IUsersService extends IService<Users> {
    Users getUserByUsername(@Param("username") String username);

    Users getUserByUid(String uid);

    Object insertUser(Users users);

    Object checkUser(LoginDto loginDto);

    Result changePassword(String token,String password, String oldpassword, Long uid);
}
