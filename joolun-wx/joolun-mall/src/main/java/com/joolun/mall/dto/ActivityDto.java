package com.joolun.mall.dto;

import com.joolun.mall.entity.Activity;
import com.joolun.mall.entity.ActivityPriceCase;
import com.joolun.mall.entity.Course;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ActivityDto implements Serializable {

    private Activity activity;

    private List<ActivityPriceCase> priceCases;

    private List<Course> courses;

}
