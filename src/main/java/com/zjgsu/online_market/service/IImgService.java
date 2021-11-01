package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.entity.Img;
import com.zjgsu.online_market.mapper.ImgMapper;

import javax.jnlp.BasicService;
import java.util.List;

public interface IImgService extends IService<Img> {
    public List<String> getUrlByGid(Long gid);

}
