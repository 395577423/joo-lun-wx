package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 书籍问题对象 book_question
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("course_question")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "书籍问题对象")
public class CourseQuestion extends Model<CourseQuestion> {
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

    @TableField(exist = false)
    private List<CourseQuestionChoice> choices = new ArrayList<>();

    /**
     * 用户答案
     */
    @TableField(exist = false)
    private Long choiceId;

    /**
     * 是否正确
     */
    @TableField(exist = false)
    private Boolean correct;

}
