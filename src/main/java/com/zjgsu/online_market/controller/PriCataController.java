package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.service.IPriCataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriCataController {
    @Autowired
    private IPriCataService priCataService;

    @GetMapping("/pri")
    public Result getAll()
    {
        return Result.success(priCataService.getAll());
    }
}
