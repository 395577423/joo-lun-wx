package com.joolun.mall.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Owen
 * @date 2022/5/12 22:30
 */
@Data
@ToString
public class QuestionDTO {
    private Long id;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 问题
     */
    private String question;

    /**
     * 排序
     */
    private Long sort;

    private String answer;

    private String title;


}
