package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.Course;

/**
 * 课程Service接口
 * 
 * @author www.joolun.com
 * @date 2021-12-08
 */
public interface ICourseService extends IService<Course>
{
    Course selectPlan();
}
