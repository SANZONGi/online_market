package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.Img;

import java.util.List;

public interface IImgService extends IService<Img> {
    List<String> getUrlByGid(Long gid);

}
