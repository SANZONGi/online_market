package com.zjgsu.online_market.controller;

import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.PriCata;
import com.zjgsu.online_market.entity.SecCata;
import com.zjgsu.online_market.service.IPriCataService;
import com.zjgsu.online_market.service.ISecCataService;
import com.zjgsu.online_market.service.impl.PriCataServiceImpl;
import com.zjgsu.online_market.service.impl.SecCataServiceImpl;
import io.swagger.annotations.ApiOperation;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SANZONG
 */
@RestController
@RequestMapping("/v2.0")
public class PriCataController {
    private IPriCataService priCataService;
    private ISecCataService secCataService;

    @Autowired
    public void setPriCataService(PriCataServiceImpl priCataService){
        this.priCataService = priCataService;
    }
    @Autowired
    public void setSecCataService(SecCataServiceImpl secCataService){
        this.secCataService = secCataService;
    }

    @ApiOperation("获取一级目录")
    @GetMapping("/pri")
    public Result getAllPri() {
        return Result.success(priCataService.getAll());
    }


    @ApiOperation("获取一级目录和二级目录")
    @GetMapping("/cata/all")
    public Result getAll() {
        List<PriCata> priCataList = priCataService.getAll();

        List<Pair<PriCata,List<SecCata>>> res = priCataList
                .stream()
                .map(priCata -> new Pair<>(priCata,secCataService.getAllInPri(priCata.getId())))
                .collect(Collectors.toList());
        return Result.success(res);
    }



    @ApiOperation("同时插入一二级目录")
    @PutMapping("/pri_sec")
    public Result addPriAndSec(@NotNull @NotBlank String priName,@NotNull @NotBlank String secName)
    {
        int res = priCataService.insertPriAndSec(priName,secName);
        if (res == 1)
        {
            return Result.fail("请检查一级目录名是否正确或者重复");
        }else if (res == 2)
        {
            return Result.fail("请检查二级目录名是否正确或者重复");
        }
        return Result.success("ok");
    }
}
