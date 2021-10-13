package com.zjgsu.online_market.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Users;
import com.zjgsu.online_market.service.impl.UsersServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Slf4j
@RestController
public class UsersController {

    @Autowired
    UsersServiceImpl usersService;

    //@Requestbody负责封装类，不能处理form-data
    @PostMapping("/users/checkuser")
    public Object checkuser(@RequestBody LoginDto loginDto) {
        return usersService.checkUser(loginDto);
    }

    @PostMapping("/users/insertuser")
    public Object insertuser(@RequestParam("username") String username, @RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("address") String address) {
        if (username == null || username.equals("") || phone == null || phone.length() != 11 || password== null || address == null) {
            return Result.fail(400,"传入数据错误",1);
        }
        return usersService.insertUser(username,phone,password,address);
    }

    @PostMapping("/users/changepassword")
    public Result changePassword(@RequestParam("password") String password,@RequestParam("oldpassword") String oldpassword,@RequestParam("uid") Long uid) {
        Users users = usersService.getOne(new QueryWrapper<Users>().eq("uid",uid));
        if (users == null) {
            return Result.fail(201,"用户不存在",null);
        }
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        String oldpass_md5str = DigestUtils.md5DigestAsHex(oldpassword.getBytes());
        if (oldpass_md5str.equals(users.getPassword()))
        {
            updateWrapper.set("password",DigestUtils.md5DigestAsHex(password.getBytes()));
            users.setUid(null).setUsername(null).setPhone(null);
            usersService.getBaseMapper().update(users,updateWrapper);
            return Result.success(200,"成功",null);
        } else
        {
            return Result.fail(206,"密码错误",null);
        }
    }

}
