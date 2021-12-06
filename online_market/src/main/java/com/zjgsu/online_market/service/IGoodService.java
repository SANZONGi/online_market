package com.zjgsu.online_market.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjgsu.online_market.common.dto.PageDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.zjgsu.online_market.entity.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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

    Integer publish(Good good, List<MultipartFile> files) throws IOException;

    Good getGoodById(Long id);

    Result frozeGoodById(Long gid);

    Result unFrozenGood(Long gid);

    @Deprecated
    List<Good> getGoodByPri(@NotNull Integer pri);

    @Deprecated
    List<Good> getGoodBySec(@NotNull Integer pri,@NotNull Integer sec);

//    @Deprecated
//    List<Good> getGoodBySearch(@NotNull @NotBlank String val);

    Page getGoodDtoListByStatus(List<Integer> status, @NotNull PageDto pageDto);


    Page getGoodDtoListBySearch(String val, @NotNull PageDto pageDto, @NotNull @NotEmpty List<Integer> status);

    Page getGoodDtoListByCata(Integer pri,Integer sec, @NotNull PageDto pageDto,@NotNull @NotEmpty List<Integer> status);

}