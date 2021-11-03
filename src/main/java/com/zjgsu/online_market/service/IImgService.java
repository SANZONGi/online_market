package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.Img;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IImgService extends IService<Img> {
    List<String> getUrlByGid(Long gid);

}
