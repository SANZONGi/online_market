package com.zjgsu.online_market.controller;


import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.annotations.RoleRequired;
import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Users;
import com.zjgsu.online_market.service.IUsersService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @ApiOperation("检查用户")
    @PostMapping("/users/check")
    public Object checkUser(HttpServletRequest request,@RequestBody @Validated LoginDto loginDto) {
        return usersService.checkUser(request,loginDto);
    }

    @ApiOperation("修改用户信息")
    @LoginRequired(required = true)
    @PostMapping("/users/{id}")
    public Result updateUser(@PathVariable @NotNull Long id,@RequestBody @NotNull Users users) {
        if (users.getPhone() == null || !users.getPhone().matches("[0-9]{11}"))
        {
            return Result.fail("电话格式错误或空",1);
        }
        if (users.getAddress() == null || users.getAddress().equals(""))
        {
            return Result.fail("地址格式错误或空",2);
        }
        int res = usersService.updateUser(id,users);
        if (res == 0)
            return Result.fail("用户不存在");
        else
            return Result.success("ok");
    }

    @ApiOperation("注册用户")
    @PutMapping("/users")
    public Result addUser(@RequestBody @Validated Users users) {
        Integer res = usersService.insertUser(users);
        if (res == 1) {
            return Result.success("成功");
        } else {
            return Result.fail("创建失败", res);
        }
    }

    @ApiOperation("查看所有已注册用户")
    @LoginRequired(required = true)
    @RoleRequired(required = true)
    @GetMapping("/users")
    public Result getAllUsers() {
        return Result.success(usersService.getAllUsers());
    }


    @ApiOperation("退出登录")
    @LoginRequired(required = true)
    @PostMapping("/users/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("role");
        request.getSession().removeAttribute("user");
        return Result.success(0);
    }


    @ApiOperation("修改密码")
    @LoginRequired(required = true)
    @PostMapping("/users")
    public Result changePassword(@RequestHeader("token") String token, @NotNull(message = "密码不能为空") String password, @NotNull(message = "旧密码不能为空") String oldpassword, @NotNull(message = "uid不能为空") Long uid) {
        return usersService.changePassword(token, password, oldpassword, uid);
    }

}
