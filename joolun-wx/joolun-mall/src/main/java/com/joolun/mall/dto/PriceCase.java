package com.joolun.mall.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-28
 */
@Data
public class PriceCase {

    private String subTitle;

    private BigDecimal salesPrice;

    private BigDecimal discountPrice;

    private int[] subType;
}
