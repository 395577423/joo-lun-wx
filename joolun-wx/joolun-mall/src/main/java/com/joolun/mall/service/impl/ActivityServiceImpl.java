package com.joolun.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.mall.dto.ActivityDto;
import com.joolun.mall.dto.ActivityRelateCourseDto;
import com.joolun.mall.entity.Activity;
import com.joolun.mall.entity.ActivityPriceCase;
import com.joolun.mall.entity.ActivityRelatedCourse;
import com.joolun.mall.mapper.ActivityMapper;
import com.joolun.mall.service.ActivityPriceCaseService;
import com.joolun.mall.service.IActivityCategoryService;
import com.joolun.mall.service.IActivityRelatedCourseService;
import com.joolun.mall.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 社会活动Service业务层处理
 *
 * @author Owen
 * @date 2022-08-12
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {


    @Autowired
    private IActivityCategoryService activityCategoryService;


    @Autowired
    private ActivityPriceCaseService activityPriceCaseService;

    @Autowired
    private IActivityRelatedCourseService activityRelatedCourseService;


    @Override
    public int add(ActivityDto activityDto, SysUser user) {
        Activity activity = activityDto.getActivity();
        activity.setPublished(false);
        activity.setCreator(user.getUserName());
        activity.setCreatorId(user.getUserId());
        boolean save = this.save(activity);
        List<ActivityPriceCase> activityPriceCases = activityDto.getPriceCases();
        if (CollectionUtil.isNotEmpty(activityPriceCases)) {
            for (ActivityPriceCase activityPriceCase : activityPriceCases) {
                activityPriceCase.setActivityId(activity.getId());
            }
        }
        activityPriceCaseService.saveBatch(activityPriceCases);
        return save ? 1 : 0;
    }

    @Override
    public int edit(ActivityDto activityDto) {
        Activity activity = activityDto.getActivity();
        boolean success = this.updateById(activityDto.getActivity());
        List<ActivityPriceCase> activityPriceCases = activityDto.getPriceCases();
        if (CollectionUtil.isNotEmpty(activityPriceCases)) {
            for (ActivityPriceCase activityPriceCase : activityPriceCases) {
                activityPriceCase.setActivityId(activity.getId());
            }
        }
        activityPriceCaseService.remove(Wrappers.<ActivityPriceCase>lambdaQuery().eq(ActivityPriceCase::getActivityId, activity.getId()));
        activityPriceCaseService.saveBatch(activityPriceCases);
        return success?1:0;
    }

    /**
     * 活动关联课程
     *
     * @param activityRelateCourseDto
     */
    @Override
    public void doRelateCourse(ActivityRelateCourseDto activityRelateCourseDto) {
        Long activityId = activityRelateCourseDto.getActivityId();
        activityRelatedCourseService.remove(Wrappers.lambdaQuery(ActivityRelatedCourse.class)
                .eq(ActivityRelatedCourse::getActivityId, activityId));
        Long[] courseIds = activityRelateCourseDto.getCourseIds();
        List<ActivityRelatedCourse> activityRelatedCourses = new ArrayList<>(courseIds.length);
        for (Long courseId : courseIds) {
            ActivityRelatedCourse activityRelatedCourse = new ActivityRelatedCourse();
            activityRelatedCourse.setActivityId(activityId);
            activityRelatedCourse.setCourseId(courseId);
            activityRelatedCourses.add(activityRelatedCourse);
        }
        activityRelatedCourseService.saveBatch(activityRelatedCourses);
    }

    /**
     * 查看课程关联的活动信息
     *
     * @param courseId
     * @return
     */
    @Override
    public List<Activity> listByCourseId(Long courseId) {

        QueryWrapper<ActivityRelatedCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ActivityRelatedCourse::getCourseId, courseId);
        List<ActivityRelatedCourse> activityRelatedCourses = activityRelatedCourseService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(activityRelatedCourses)) {
            List<Long> activityIds = activityRelatedCourses.stream().map(ActivityRelatedCourse::getActivityId)
                    .collect(Collectors.toList());
            List<Activity> activities = this.listByIds(activityIds);
            return activities;
        }
        return null;
    }


}
