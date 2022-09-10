package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.mall.dto.ActivityDto;
import com.joolun.mall.dto.ActivityRelateCourseDto;
import com.joolun.mall.entity.Activity;

import java.util.List;

/**
 * 社会活动Service接口
 * 
 * @author Owen
 * @date 2022-08-12
 */
public interface IActivityService extends IService<Activity>
{

    int add(ActivityDto activityDto, SysUser user);

    int edit(ActivityDto activityDto);

    /**
     * 活动关联课程
     * @param activityRelateCourseDto
     */
    void doRelateCourse(ActivityRelateCourseDto activityRelateCourseDto);

    /**
     * 查看课程关联的活动信息
     * @param courseId
     * @return
     */
    List<Activity> listByCourseId(Long courseId);

}
