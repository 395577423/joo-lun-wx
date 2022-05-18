package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.dto.QuestionDTO;
import com.joolun.mall.dto.QuestionDetailDTO;
import com.joolun.mall.dto.QuestionQuery;
import com.joolun.mall.entity.CourseQuestion;
import com.joolun.mall.mapper.CourseQuestionMapper;
import com.joolun.mall.service.ICourseQuestionService;
import org.springframework.stereotype.Service;

/**
 * 书籍问题Service业务层处理
 *
 * @author Owen
 * @date 2021-12-08
 */
@Service
public class CourseQuestionServiceImpl extends ServiceImpl<CourseQuestionMapper, CourseQuestion> implements ICourseQuestionService {

    @Override
    public Page<QuestionDTO> page2(Page page, QuestionQuery query) {
        return baseMapper.page2(page,query);
    }

    @Override
    public QuestionDetailDTO getQuestion(Long id) {
        return baseMapper.getQuestion(id);
    }
}
