package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.service.IImgService;
import com.zjgsu.online_market.service.impl.ImgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author SANZONG
 */
@Controller
@RestController
@RequestMapping("/v2.0")
public class ImgController {

    private IImgService imgService;

    @Autowired
    public void setImgService(ImgServiceImpl imgService){
        this.imgService = imgService;
    }

    @GetMapping("/img/{gid}")
    public List<String> getUrlByGid(@PathVariable @NotNull Long gid) {
        return imgService.getUrlByGid(gid);
    }

}
