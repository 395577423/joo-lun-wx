package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.ActivityRelatedCourse;
import com.joolun.mall.entity.Course;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Owen
 * @date 2022-08-12
 */
public interface IActivityRelatedCourseService extends IService<ActivityRelatedCourse> {

    /**
     * 获取关联课程
     *
     * @param activityId
     * @return
     */
    List<Course> getRelateCourse(Long activityId);
}
