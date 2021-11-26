package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Users;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
public interface IUsersService extends IService<Users> {
    Users getUserByUsername(String username);

    Users getUserByUid(String uid);

    Integer insertUser(Users users);

    Object checkUser(HttpServletRequest request, LoginDto loginDto);

    List<Users> getAllUsers();

    Result changePassword(String password, String oldpassword, Long uid);

    Integer updateUser(Long uid,Users users);
}
