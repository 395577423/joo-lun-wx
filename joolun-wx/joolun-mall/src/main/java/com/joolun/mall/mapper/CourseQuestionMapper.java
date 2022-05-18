package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.mall.dto.QuestionDTO;
import com.joolun.mall.dto.QuestionDetailDTO;
import com.joolun.mall.dto.QuestionQuery;
import com.joolun.mall.entity.CourseQuestion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 书籍问题Mapper接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface CourseQuestionMapper extends BaseMapper<CourseQuestion> {

    Page<QuestionDTO> page2(Page page, @Param("query") QuestionQuery query);

    @Select("select i.title,i.id as courseId,j.id as questionId,j.question,j.answer,j.sort from course i,course_question j where i.id = j.course_id and j.id = #{id}")
    QuestionDetailDTO getQuestion(@Param("id") Long id);
}
