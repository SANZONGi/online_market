package com.zjgsu.online_market.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjj
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Good implements Serializable {

    private Long uid;

    private Long gid;

    private String gname;

    private String image;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private String description;


}
