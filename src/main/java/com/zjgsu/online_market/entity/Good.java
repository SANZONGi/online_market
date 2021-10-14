package com.zjgsu.online_market.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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

    private Double price;

    private Integer stock;

    private Integer status;

    private String description;


}
