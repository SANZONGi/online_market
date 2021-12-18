package com.zjgsu.online_market.service.impl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    private static final Integer LOWESTSTRENGTH = 2;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EncypterUtil encypterUtil;
    @Autowired
    private RoleMapper roleMapper;


    public Integer checkPasswordStrength(String pwd) {
        int strength;
        if (pwd == null) {
            return 0;
        }
        pwd = pwd.trim();
        strength = pwd.length() >= 9?1:0;
        if (pwd.matches(".*[A-Z].*") || pwd.matches(".*[a-z].*")) {
            strength++;
        }
        return strength;
    }

    @Override
    public Users getUserByUsername(String username) {
        /**
         * redis中有记录，200s
         */
        if (redisTemplate.hasKey(username)) {
            return (Users) redisTemplate.opsForValue().get(username);
        } else {
            Users users = usersMapper.getUserByUsername(username);
            redisTemplate.opsForValue().set(username, users, 200, TimeUnit.SECONDS);
            return users;
        }
    }

    @Override
    public Users getUserByUid(String uid) {
        if (redisTemplate.hasKey(uid)) {
            return (Users) redisTemplate.opsForValue().get(uid);
        } else {
            Users users = usersMapper.selectById(uid);
            redisTemplate.opsForValue().set(uid, users, 200, TimeUnit.SECONDS);
            return users;
        }
    }

    @Override
    @Transactional
    public Integer insertUser(Users users) {
        if (usersMapper.getUserByUsername(users.getUsername()) != null) {
            return 2;
        } else {
            if (checkPasswordStrength(users.getPassword()) < LOWESTSTRENGTH)
            {
                return 4;
            }
            String enc = encypterUtil.Encrypt(users.getPassword());
            users.setPassword(enc);
            try {     //防止过程中用户名被注册
                usersMapper.insertHasKey(users);
                roleMapper.insert(new Role().setRole(RoleServiceImpl.USER_ROLE).setUid(users.getUid()));
                return 1;
            } catch (Exception e) {
                return 3;
            }
        }
    }

    @Override
    public List<Users> getAllUsers()
    {
        return usersMapper.getAllUsers();
    }

    @Override
    public Integer updateUser(Long uid, Users users) {
        users.setPassword(null);
        users.setUsername(null);
        users.setUid(null);
        return usersMapper.update(users,new UpdateWrapper<Users>().eq("uid",uid));
    }

    @Override
    @Transactional(readOnly = true)
    public Object checkUser(HttpServletRequest request, LoginDto loginDto) {
        Users users = getUserByUsername(loginDto.getUsername());
        if (users == null) {
            return Result.fail(401, "用户不存在", 1);
        }
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("uid", users.getUid()));
        if (role == null) {
            return Result.fail("用户权限出错，请联系管理员", 3);
        }
        if (encypterUtil.Decrypt(users.getPassword()).equals(loginDto.getPwd())) {
            Map<String, String> map = new HashMap<>(20);
            map.put("uid", users.getUid().toString());
            map.put("username", users.getUsername());
            String token = jwtUtils.generateToken(map);
            request.getSession().setAttribute("user", users);
            request.getSession().setAttribute("role", role);
            users.setPassword(null).setPhone(null).setAddress(null);
            return Result.success(200, token, users);
        } else {
            return Result.fail(401, "密码或用户名错误", 2);
        }
    }

    @Override
    @Transactional
    public Result changePassword(String password, String oldpassword, Long uid) {
        Users users = getUserByUid(uid.toString());
        if (users == null) {
            return Result.fail(401, "用户不存在", 1);
        }
        if (checkPasswordStrength(password) < LOWESTSTRENGTH) {
            return Result.fail( "强度过低", 3);
        }
        //数据库里的真实密码
        String truePwd = encypterUtil.Decrypt(users.getPassword());

        if (truePwd.equals(oldpassword)) {

            UpdateWrapper<Users> updateWrapper = new UpdateWrapper<Users>().eq("uid", users.getUid());
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
