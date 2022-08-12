package com.joolun.mall.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;
import lombok.Data;

/**
 * 【请填写功能名称】对象 activity_related_course
 * 
 * @author Owen
 * @date 2022-08-12
 */
@Data
public class ActivityRelatedCourse extends Model<ActivityRelatedCourse>
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 活动id */
    @Excel(name = "活动id")
    private Long activityId;

    /** 课程id */
    @Excel(name = "课程id")
    private Long courseId;

}
