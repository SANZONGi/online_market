package com.zjgsu.online_market.service;

import com.zjgsu.online_market.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
public interface IUsersService extends IService<Users>{
    public Users getUserByUsername(@Param("username") String username);

}
