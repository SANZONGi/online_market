package com.zjgsu.online_market.controller;


import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    private IUsersService usersService;

    @PostMapping("/users/checkuser")
    public Object checkuser(@RequestBody @Validated LoginDto loginDto) {
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
        return  usersService.changePassword(password,oldpassword,uid);
    }

}
