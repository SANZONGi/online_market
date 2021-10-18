package com.zjgsu.online_market.controller;


import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Users;
import com.zjgsu.online_market.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

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
    private IUsersService usersService;

    @PostMapping("/users/checkuser")
    public Object checkuser(@RequestBody @Validated LoginDto loginDto) {
        return usersService.checkUser(loginDto);
    }

    @PostMapping("/users/insertuser")
    public Object insertuser(@RequestBody @Validated Users users) {

        return usersService.insertUser(users);
    }

    @PostMapping("/users/changepassword")
    public Result changePassword(@RequestHeader("token") String token,@NotNull(message = "密码不能为空") String password,@NotNull(message = "旧密码不能为空") String oldpassword,@NotNull(message = "uid不能为空") Long uid) {
        return  usersService.changePassword(token, password,oldpassword,uid);
    }

}
