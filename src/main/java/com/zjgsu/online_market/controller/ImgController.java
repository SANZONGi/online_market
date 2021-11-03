package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RestController
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private IImgService imgService;

    @GetMapping("/{gid}")
    public List<String> getUrlByGid(@PathVariable @NotNull Long gid) {
        return imgService.getUrlByGid(gid);
    }

}
