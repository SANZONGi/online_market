package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.entity.Role;
import com.zjgsu.online_market.mapper.RoleMapper;
import com.zjgsu.online_market.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
