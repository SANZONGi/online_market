package com.zjgsu.online_market.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjgsu.online_market.common.annotations.LoginRequired;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Role;
import com.zjgsu.online_market.service.IRoleService;
import com.zjgsu.online_market.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author SANZONG
 */
@RestController
@RequestMapping("/v2.0")
public class RoleController {
    private IRoleService roleService;

    @Autowired
    public void setRoleService(RoleServiceImpl roleService){
        this.roleService = roleService;
    }

    @GetMapping("/role/{uid}")
    @LoginRequired(required = true)
    public Result getRoleById(@PathVariable @NotNull Long uid) {
        Role role = roleService.getOne(new QueryWrapper<Role>().eq("uid",uid));
        if (role == null){
            return Result.fail("无效uid");
        }
        return Result.success(role);
    }

}
