package com.joolun.mall.dto;

import lombok.Data;

/**
 * @author Owen
 * @date 2022/5/12 22:24
 */
@Data
public class QuestionQuery {

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程问题
     */
    private String name;
}
