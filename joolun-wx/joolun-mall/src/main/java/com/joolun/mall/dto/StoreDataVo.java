package com.joolun.mall.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lanjian
 * @Description
 * @create 2022-10-07
 */
@Data
public class StoreDataVo implements Serializable {

    private String nickName;

    private int subCount;

    private int memberCount;

    private BigDecimal totalAmount;

    private BigDecimal completedAmount;

    private BigDecimal withDrawAmount;

}
