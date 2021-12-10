package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 书籍故事对象 book_story
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("course_story")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "书籍故事对象")
public class CourseStory extends Model<CourseStory> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 课程ID
     */
    @Excel(name = "课程ID")
    private Long courseId;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;
}
