package com.joolun.mall.dto;

import com.joolun.mall.entity.CourseQuestionChoice;
import lombok.Data;

import java.util.List;

/**
 * @author Owen
 * @date 2022/5/18 20:47
 */
@Data
public class QuestionDetailDTO {
    private String title;
    private Long courseId;
    private Long questionId;
    private Long sort;
    private String question;
    private String answer;
    private List<CourseQuestionChoice> choices;
}
