package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.dto.QuestionDTO;
import com.joolun.mall.dto.QuestionDetailDTO;
import com.joolun.mall.dto.QuestionQuery;
import com.joolun.mall.entity.CourseQuestion;

/**
 * 书籍问题Service接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface ICourseQuestionService extends IService<CourseQuestion> {
    Page<QuestionDTO> page2(Page page, QuestionQuery query);

    QuestionDetailDTO getQuestion(Long id);
}
