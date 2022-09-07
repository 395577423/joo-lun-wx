package com.joolun.mall.dto;

import com.joolun.mall.enums.ProductTypeEnum;
import lombok.Data;

/**
 * @author lanjian
 * @Description
 * @create 2022-09-07
 */
@Data
public class UserOrderBaseInfo {

    private String userId;

    private String orderNo;

    private Long priceCaseId;

    ProductTypeEnum productType;

}
