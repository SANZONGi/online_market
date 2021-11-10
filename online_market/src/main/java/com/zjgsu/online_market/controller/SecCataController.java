package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.service.ISecCataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2.0")
public class SecCataController {
    @Autowired
    private ISecCataService secCataService;

    @GetMapping("/sec")
    public Result getAll() {
        return Result.success(secCataService.getAll());
    }
}
