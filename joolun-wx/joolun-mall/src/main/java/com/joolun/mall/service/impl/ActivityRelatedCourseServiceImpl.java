package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.ActivityRelatedCourse;
import com.joolun.mall.entity.Course;
import com.joolun.mall.mapper.ActivityRelatedCourseMapper;
import com.joolun.mall.service.IActivityRelatedCourseService;
import com.joolun.mall.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Owen
 * @date 2022-08-12
 */
@Service
public class ActivityRelatedCourseServiceImpl extends ServiceImpl<ActivityRelatedCourseMapper, ActivityRelatedCourse> implements IActivityRelatedCourseService {

    @Autowired
    ICourseService courseService;



    @Override
    public List<Course> getRelateCourse(Long activityId) {
        LambdaQueryWrapper<ActivityRelatedCourse> queryWrapper = Wrappers.lambdaQuery(ActivityRelatedCourse.class)
                .eq(ActivityRelatedCourse::getActivityId, activityId);
        List<ActivityRelatedCourse> activityRelatedCourses = this.list(queryWrapper);
        if (!activityRelatedCourses.isEmpty()) {
            List<Long> courseIdList = activityRelatedCourses.stream()
                    .map(ActivityRelatedCourse::getCourseId).collect(Collectors.toList());
            List<Course> courses = courseService.listByIds(courseIdList);
            return courses;
        }
        return null;
    }
}
