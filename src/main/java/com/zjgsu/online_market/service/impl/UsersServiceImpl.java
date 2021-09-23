package com.zjgsu.online_market.service.impl;

import com.zjgsu.online_market.entity.Users;
import com.zjgsu.online_market.mapper.UsersMapper;
import com.zjgsu.online_market.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper,Users> implements IUsersService{
    @Autowired
    private UsersMapper usersMapper;

    public Users checkUserByUsername(@Param("username") String username) {
        return usersMapper.checkUserByUsername(username);
    }
    public void insertUser(@Param("username") String username,@Param("password") String password,@Param("phone") String phone,@Param("address") String address)
    {
        usersMapper.insertUser(username,password,phone,address);
    }
}
