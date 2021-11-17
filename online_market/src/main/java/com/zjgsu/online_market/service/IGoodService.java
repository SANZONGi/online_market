package com.zjgsu.online_market.service;

import com.zjgsu.online_market.common.dto.GoodDto;
import com.zjgsu.online_market.common.lang.Result;
import com.zjgsu.online_market.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
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

    List<Good> getGoodByPri(@NotNull Integer pri);

    List<Good> getGoodBySec(@NotNull Integer pri,@NotNull Integer sec);

    List<Good> getGoodBySearch(@NotNull @NotBlank String val);

    GoodDto getGoodDto(Long gid);
}