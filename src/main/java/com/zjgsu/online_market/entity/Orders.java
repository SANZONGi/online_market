package com.zjgsu.online_market.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class Orders implements Serializable {
    private Long gid;

    private Long oid;

    private String username;

    private String useraddress;

    private String userphone;

    private Integer number;

    private BigDecimal amount;

    private LocalDateTime date;

    private Integer status;

}
