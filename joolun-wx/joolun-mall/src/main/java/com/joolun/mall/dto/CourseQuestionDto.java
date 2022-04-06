package com.joolun.mall.dto;

import com.joolun.common.annotation.Excel;
import lombok.Data;

/**
 * @author Owen
 * @date 2022/4/6 21:37
 */
@Data
public class CourseQuestionDto {
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
     * 封面图片
     */
    @Excel(name = "封面图片")
    private String imageUrl;

    /**
     * 问题
     */
    @Excel(name = "问题")
    private String question;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 正确答案
     */
    private String answer;
}
