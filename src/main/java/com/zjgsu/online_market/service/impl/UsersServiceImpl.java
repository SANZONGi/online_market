package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.common.utils.JwtUtils;
import com.zjgsu.online_market.entity.Users;
import com.zjgsu.online_market.mapper.UsersMapper;
import com.zjgsu.online_market.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

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

    public Users getUserByUsername(String username) {
        return usersMapper.getUserByUsername(username);
    }

    public Object insertUser(String username,String password,String phone,String address) {
        Users user = usersMapper.getUserByUsername(username);
        if (user != null) {
            return Result.fail(400, "该用户已存在", 2);
        } else {
            Users new_user = new Users();
            String md5Str = DigestUtils.md5DigestAsHex(password.getBytes());
            new_user.setPassword(md5Str).setUsername(username).setPhone(phone).setAddress(address);
            usersMapper.insert(new_user);
            return Result.success("成功");
        }






    }

    public Object checkUser(LoginDto loginDto) {
        if (usersMapper.selectOne(new QueryWrapper<Users>().eq("username",loginDto.getUsername())) == null){
            return Result.fail(401,"用户不存在",1);
        }
        Users users = getUserByUsername(loginDto.getUsername());
        String md5Str = DigestUtils.md5DigestAsHex(loginDto.getPwd().getBytes());
        if (users != null && users.getPassword().equals(md5Str)) {
            Map<String,String> map = new HashMap<>();
            map.put("uid",users.getUid().toString());
            map.put("username",users.getUsername());
            String token = JwtUtils.generateToken(map);
            users.setPassword(null).setPhone(null).setAddress(null);
            return Result.success(200, token, users);
        } else {
            return Result.fail(401,"密码或用户名错误",2);
        }
    }


}
