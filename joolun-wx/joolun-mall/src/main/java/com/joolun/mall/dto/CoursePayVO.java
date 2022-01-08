package com.joolun.mall.dto;

import lombok.Data;

/**
 * <p>课程支付VO</p>
 *
 * @author Owen
 * @date 2022-01-07 14:32:02
 **/
@Data
public class CoursePayVO {
    private Long id;
    private String userId;
    private String useCoupon;
}
