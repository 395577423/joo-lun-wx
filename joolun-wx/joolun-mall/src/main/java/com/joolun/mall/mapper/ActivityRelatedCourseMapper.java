package com.joolun.mall.mapper;

import com.joolun.mall.entity.ActivityRelatedCourse;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author Owen
 * @date 2022-08-12
 */
public interface ActivityRelatedCourseMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ActivityRelatedCourse selectActivityRelatedCourseById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param activityRelatedCourse 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ActivityRelatedCourse> selectActivityRelatedCourseList(ActivityRelatedCourse activityRelatedCourse);

    /**
     * 新增【请填写功能名称】
     * 
     * @param activityRelatedCourse 【请填写功能名称】
     * @return 结果
     */
    public int insertActivityRelatedCourse(ActivityRelatedCourse activityRelatedCourse);

    /**
     * 修改【请填写功能名称】
     * 
     * @param activityRelatedCourse 【请填写功能名称】
     * @return 结果
     */
    public int updateActivityRelatedCourse(ActivityRelatedCourse activityRelatedCourse);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteActivityRelatedCourseById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteActivityRelatedCourseByIds(Long[] ids);

    /**
     * 根据activityId删除数据
     * @param activityId
     * @return
     */
    int deleteActivityRelatedCourseByByActivityId(Long activityId);
}
