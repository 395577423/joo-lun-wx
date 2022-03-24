package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.CourseQuestionChoice;
import com.joolun.mall.mapper.CourseQuestionChoiceMapper;
import com.joolun.mall.service.ICourseQuestionChoiceService;
import org.springframework.stereotype.Service;

/**
 * 书籍问题选项Service业务层处理
 *
 * @author Owen
 * @date 2021-12-08
 */
@Service
public class CourseQuestionChoiceServiceImpl extends ServiceImpl<CourseQuestionChoiceMapper, CourseQuestionChoice> implements ICourseQuestionChoiceService {
}
