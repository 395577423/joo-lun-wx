package com.joolun.mall.service;

import com.joolun.mall.dto.ActivityRelateCourseDto;
import com.joolun.mall.entity.Activity;

import java.util.List;

/**
 * 社会活动Service接口
 * 
 * @author Owen
 * @date 2022-08-12
 */
public interface IActivityService 
{
    /**
     * 查询社会活动
     * 
     * @param id 社会活动ID
     * @return 社会活动
     */
    public Activity selectActivityById(Long id);

    /**
     * 查询社会活动列表
     * 
     * @param activity 社会活动
     * @return 社会活动集合
     */
    public List<Activity> selectActivityList(Activity activity);

    /**
     * 新增社会活动
     * 
     * @param activity 社会活动
     * @return 结果
     */
    public int insertActivity(Activity activity);

    /**
     * 修改社会活动
     * 
     * @param activity 社会活动
     * @return 结果
     */
    public int updateActivity(Activity activity);

    /**
     * 批量删除社会活动
     * 
     * @param ids 需要删除的社会活动ID
     * @return 结果
     */
    public int deleteActivityByIds(Long[] ids);

    /**
     * 删除社会活动信息
     * 
     * @param id 社会活动ID
     * @return 结果
     */
    public int deleteActivityById(Long id);

    /**
     * 活动关联课程
     * @param activityRelateCourseDto
     */
    void doRelateCourse(ActivityRelateCourseDto activityRelateCourseDto);
}
