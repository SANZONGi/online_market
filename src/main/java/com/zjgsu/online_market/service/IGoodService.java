package com.zjgsu.online_market.service;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */

public interface IGoodService extends IService<Good> {
    List<Good> getFrozenGood();

    Boolean publish(Good good);

    Good getGoodById(Long id);

    Result frozeGoodById(Long gid);

    Result unFrozenGood(Long gid);
}