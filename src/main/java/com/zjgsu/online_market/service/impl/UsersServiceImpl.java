package com.zjgsu.online_market.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.common.dto.LoginDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.common.utils.EncypterUtil;
import com.zjgsu.online_market.common.utils.JwtUtils;
import com.zjgsu.online_market.entity.Role;
import com.zjgsu.online_market.entity.Users;
import com.zjgsu.online_market.mapper.RoleMapper;
import com.zjgsu.online_market.mapper.UsersMapper;
import com.zjgsu.online_market.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EncypterUtil encypterUtil;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private RoleMapper roleMapper;
    public Users getUserByUsername(String username) {
        if (redisTemplate.hasKey(username)) {     //redis中有记录，200s
            return (Users) redisTemplate.opsForValue().get(username);
        } else {
            Users users = usersMapper.getUserByUsername(username);
            redisTemplate.opsForValue().set(username, users, 200, TimeUnit.SECONDS);
            return users;
        }
    }

    public Users getUserByUid(String uid) {
        if (redisTemplate.hasKey(uid)) {     //redis中有记录，200s
            return (Users) redisTemplate.opsForValue().get(uid);
        } else {
            Users users = usersMapper.selectById(uid);
            redisTemplate.opsForValue().set(uid, users, 200, TimeUnit.SECONDS);
            return users;
        }
    }

    @Deprecated
    public Integer insertUser(Users users) {
        if (usersMapper.getUserByUsername(users.getUsername()) != null) {
            return 2;
        } else {
            String enc = encypterUtil.Encrypt(users.getPassword());
            users.setPassword(enc);
            usersMapper.insert(users);
            return 1;
        }
    }


    @Transactional(readOnly = true)
    public Object checkUser(LoginDto loginDto) {
        Users users = getUserByUsername(loginDto.getUsername());

        if (users == null) {
            return Result.fail(401, "用户不存在", 1);
        }
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("uid",users.getUid()));
        if (role == null)
        {
            return Result.fail("用户权限出错，请联系管理员",3);
        }
        if (encypterUtil.Decrypt(users.getPassword()).equals(loginDto.getPwd())) {

            Map<String, String> map = new HashMap<>();
            map.put("uid", users.getUid().toString());
            map.put("username", users.getUsername());
            String token = jwtUtils.generateToken(map);
            httpSession.setAttribute("user",users);
            httpSession.setAttribute("role",role);
            users.setPassword(null).setPhone(null).setAddress(null);
            return Result.success(200, token, users);
        } else {
            return Result.fail(401, "密码或用户名错误", 2);
        }
    }

    @Transactional
    public Result changePassword(String token,String password, String oldpassword, Long uid) {
        DecodedJWT tokenBody = jwtUtils.verify(token);
        //保证是本用户在网页上操作，除非既泄露了密码又泄露了token
        String tokenUserid =  tokenBody.getClaim("uid").asString();
        if (!tokenUserid.equals(String.valueOf(uid)))
        {
            //forbidden
            return Result.fail(403,"用户不一致",null);
        }
        Users users = getUserByUid(uid.toString());
        if (users == null) {
            return Result.fail(401, "用户不存在", 1);
        }

        //数据库里的真实密码
        String truePwd = encypterUtil.Decrypt(users.getPassword());

        if (truePwd.equals(oldpassword)) {

            UpdateWrapper<Users> updateWrapper = new UpdateWrapper<Users>().eq("uid",users.getUid());
            users.setPassword(encypterUtil.Encrypt(password));

            usersMapper.update(users, updateWrapper);
            redisTemplate.delete(users.getUid().toString());
            redisTemplate.delete(users.getUsername());

            return Result.success(200, "成功", null);
        } else {
            return Result.fail(401, "密码错误", 2);
        }
    }
}
