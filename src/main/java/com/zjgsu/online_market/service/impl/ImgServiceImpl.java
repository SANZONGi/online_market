package com.zjgsu.online_market.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjgsu.online_market.entity.Img;
import com.zjgsu.online_market.mapper.ImgMapper;
import com.zjgsu.online_market.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ImgServiceImpl extends ServiceImpl<ImgMapper,Img> implements IImgService {
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public List<String> getUrlByGid(@NotNull Long gid) {
        return imgMapper.getUrlByGid(gid);
    }
}
