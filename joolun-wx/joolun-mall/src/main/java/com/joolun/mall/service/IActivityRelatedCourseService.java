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
public interface IActivityRelatedCourseService extends IService<ActivityRelatedCourse>
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
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteActivityRelatedCourseByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteActivityRelatedCourseById(Long id);

    /**
     * 获取关联课程
     * @param activityId
     * @return
     */
    List<Course> getRelateCourse(Long activityId);
}
