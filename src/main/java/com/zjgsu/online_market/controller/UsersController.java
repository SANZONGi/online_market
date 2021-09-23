package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.common.token.TokenProccessor;
import com.zjgsu.online_market.common.token.TokenTools;
import com.zjgsu.online_market.entity.Users;
import com.zjgsu.online_market.service.IUsersService;
import com.zjgsu.online_market.service.impl.UsersServiceImpl;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Wrapper;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@RestController
public class UsersController {

    @Autowired
    UsersServiceImpl usersService;

    @PostMapping("/users/checkuser")
    public Object checkuser(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        Users users = usersService.checkUserByUsername(loginDto.getUsername());
        String md5Str = DigestUtils.md5DigestAsHex(loginDto.getPwd().getBytes());
        if (users != null && users.getPassword().equals(md5Str)) {
            TokenTools.createToken(request,"xjj");
            return Result.success(200, request.getSession().getAttribute("xjj").toString(), users);
        } else {
            return Result.fail(201, "密码或用户名错误", users);
        }
    }

    @PostMapping("/users/insertuser")
    @ResponseBody
    public Object insertuser(@RequestParam("username") String username, @RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("address") String address) throws NoSuchAlgorithmException {
        Users user = usersService.checkUserByUsername(username);
        if (user != null) {
            return Result.fail(202, "该用户已存在", null);
        } else {
            String md5Str = DigestUtils.md5DigestAsHex(password.getBytes());
            usersService.insertUser(username,md5Str,phone,address);
            return Result.success(200, "注册成功", null);
        }
    }

    @PostMapping("/users/changepassword")
    public Result changePassword(@RequestParam("password") String password,@RequestParam("oldpassword") String oldpassword,@RequestParam("uid") Long uid)
    {
        Users users = usersService.getOne(new QueryWrapper<Users>().eq("uid",uid));
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        String oldpass_md5str = DigestUtils.md5DigestAsHex(oldpassword.getBytes());
        if (oldpass_md5str.equals(users.getPassword()))
        {
            updateWrapper.set("password",DigestUtils.md5DigestAsHex(password.getBytes()));
            users.setUid(null).setUsername(null).setPhone(null);
            usersService.getBaseMapper().update(users,updateWrapper);
            return Result.success(200,"修改成功",null);
        } else
        {
            return Result.fail(206,"密码错误",null);
        }
    }

}
