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
    /**
     * 根据用户名返回用户
     * @param username
     * @return
     */
    Users getUserByUsername(String username);

    /**
     * 根据用户id返回用户
     * @param uid
     * @return
     */
    Users getUserByUid(String uid);

    /**
     * 插入一个用户
     * @param users
     * @return
     */
    Integer insertUser(Users users);

    /**
     * 检查用户是否登录成功
     * @param request
     * @param loginDto
     * @return
     */
    Object checkUser(HttpServletRequest request, LoginDto loginDto);

    /**
     * 获取全部用户
     * @return
     */
    List<Users> getAllUsers();

    /**
     * 修改密码
     * @param password
     * @param oldpassword
     * @param uid
     * @return
     */
    Result changePassword(String password, String oldpassword, Long uid);

    /**
     * 更新用户信息，仅限电话，地址
     * @param uid
     * @param users
     * @return
     */
    Integer updateUser(Long uid,Users users);
}
