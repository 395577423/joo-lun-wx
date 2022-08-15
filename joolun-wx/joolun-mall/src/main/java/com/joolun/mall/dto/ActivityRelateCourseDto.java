package com.joolun.mall.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-15
 */
@Data
public class ActivityRelateCourseDto implements Serializable {

    private Long activityId;

    private Long[] courseIds;

}
