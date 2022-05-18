package com.joolun.mall.dto;

import com.joolun.mall.entity.CourseQuestionChoice;
import lombok.Data;

import java.util.List;

/**
 * @author Owen
 * @date 2022/5/12 22:58
 */
@Data
public class FinalResultDTO {

    private QuestionDTO question;

    private List<CourseQuestionChoice> choices;
}
