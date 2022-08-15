package com.joolun.mall.service.impl;

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
    private ActivityRelatedCourseMapper activityRelatedCourseMapper;

    @Autowired
    ICourseService courseService;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ActivityRelatedCourse selectActivityRelatedCourseById(Long id) {
        return activityRelatedCourseMapper.selectActivityRelatedCourseById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param activityRelatedCourse 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ActivityRelatedCourse> selectActivityRelatedCourseList(ActivityRelatedCourse activityRelatedCourse) {
        return activityRelatedCourseMapper.selectActivityRelatedCourseList(activityRelatedCourse);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param activityRelatedCourse 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertActivityRelatedCourse(ActivityRelatedCourse activityRelatedCourse) {
        return activityRelatedCourseMapper.insertActivityRelatedCourse(activityRelatedCourse);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param activityRelatedCourse 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateActivityRelatedCourse(ActivityRelatedCourse activityRelatedCourse) {
        return activityRelatedCourseMapper.updateActivityRelatedCourse(activityRelatedCourse);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteActivityRelatedCourseByIds(Long[] ids) {
        return activityRelatedCourseMapper.deleteActivityRelatedCourseByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteActivityRelatedCourseById(Long id) {
        return activityRelatedCourseMapper.deleteActivityRelatedCourseById(id);
    }

    @Override
    public List<Course> getRelateCourse(Long activityId) {
        ActivityRelatedCourse queryBean = new ActivityRelatedCourse();
        queryBean.setActivityId(activityId);
        List<ActivityRelatedCourse> activityRelatedCourses = selectActivityRelatedCourseList(queryBean);
        if (!activityRelatedCourses.isEmpty()) {
            List<Long> courseIdList = activityRelatedCourses.stream()
                    .map(ActivityRelatedCourse::getCourseId).collect(Collectors.toList());
            List<Course> courses = courseService.listByIds(courseIdList);
            return courses;
        }
        return null;
    }
}
