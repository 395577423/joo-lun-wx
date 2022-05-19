package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.StringUtils;
import com.joolun.mall.dto.*;
import com.joolun.mall.entity.CourseQuestion;
import com.joolun.mall.entity.CourseQuestionChoice;
import com.joolun.mall.service.ICourseQuestionChoiceService;
import com.joolun.mall.service.ICourseQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 书籍问题Controller
 *
 * @author Owen
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course/question")
public class CourseQuestionController extends BaseController {
    @Autowired
    private ICourseQuestionService courseQuestionService;

    @Autowired
    private ICourseQuestionChoiceService iCourseQuestionChoiceService;

    /**
     * 查询书籍问题列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, CourseQuestion courseQuestion) {
        return AjaxResult.success(courseQuestionService.page(page, Wrappers.query(courseQuestion)));
    }

    @GetMapping("/page2")
    public AjaxResult page2(Page page, QuestionQuery query) {
        Page<QuestionDTO> questionDTOPage = courseQuestionService.page2(page, query);

        List<QuestionDTO> records = questionDTOPage.getRecords();

        List<FinalResultDTO> result = new ArrayList<>();

        for (QuestionDTO dto : records) {
            FinalResultDTO finalDto = new FinalResultDTO();

            finalDto.setQuestion(dto);
            QueryWrapper<CourseQuestionChoice> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("question_id", dto.getId());
            List<CourseQuestionChoice> list = iCourseQuestionChoiceService.list(queryWrapper);
            finalDto.setChoices(list);

            result.add(finalDto);
        }

        Page<FinalResultDTO> finalResultDTOPage = new Page<>();

        BeanUtils.copyProperties(questionDTOPage, finalResultDTOPage);

        finalResultDTOPage.setRecords(result);


        return AjaxResult.success(finalResultDTOPage);
    }

    /**
     * 获取书籍问题详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(courseQuestionService.getById(id));
    }

    /**
     * 新增书籍问题
     */
    @Log(title = "书籍问题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseQuestionDto courseQuestion) {
        CourseQuestion question = new CourseQuestion();
        BeanUtils.copyProperties(courseQuestion, question);
        return AjaxResult.success(courseQuestionService.save(question));
    }

    /**
     * 修改书籍问题
     */
    @Log(title = "书籍问题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseQuestion courseQuestion) {
        return AjaxResult.success(courseQuestionService.updateById(courseQuestion));
    }

    /**
     * 删除书籍问题
     */
    @Log(title = "书籍问题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        courseQuestionService.removeById(id);
        QueryWrapper<CourseQuestionChoice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", id);
        iCourseQuestionChoiceService.remove(queryWrapper);
        return AjaxResult.success();
    }

    /**
     * 查询问题列表
     */
    @GetMapping("/list")
    public AjaxResult list(String question) {
        QueryWrapper<CourseQuestion> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(question)) {
            queryWrapper.like("question", question);
        }
        List<CourseQuestion> list = courseQuestionService.list(queryWrapper.lambda().select(CourseQuestion::getId, CourseQuestion::getQuestion));
        return AjaxResult.success(list);
    }

    @GetMapping("/detail/{id}")
    public AjaxResult getOne(@PathVariable Long id) {

        QuestionDetailDTO detail = courseQuestionService.getQuestion(id);
        QueryWrapper<CourseQuestionChoice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", id);
        List<CourseQuestionChoice> list = iCourseQuestionChoiceService.list(queryWrapper);
        detail.setChoices(list);
        return AjaxResult.success(detail);
    }

    @PutMapping("/question-choice")
    public AjaxResult updateQuestionAndChoices(@RequestBody QuestionDetailDTO questionDTO) {
        logger.info(questionDTO.toString());
        Long questionId = questionDTO.getQuestionId();
        CourseQuestion question = courseQuestionService.getById(questionId);
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswer(questionDTO.getAnswer());
        List<CourseQuestionChoice> choices = questionDTO.getChoices();
        choices.forEach(t->t.setQuestionId(questionId));
        iCourseQuestionChoiceService.saveOrUpdateBatch(choices);
        return AjaxResult.success();
    }

    @PostMapping("/question-choice")
    public AjaxResult addQuestionAndChoices(@RequestBody QuestionDetailDTO questionDTO) {

        CourseQuestion question = new CourseQuestion();
        question.setQuestion(questionDTO.getQuestion());
        question.setAnswer(questionDTO.getAnswer());
        question.setSort(questionDTO.getSort());
        question.setCourseId(questionDTO.getCourseId());
        courseQuestionService.save(question);
        List<CourseQuestionChoice> choices = questionDTO.getChoices();
        if (Objects.nonNull(choices) && choices.size() > 0) {
            choices.forEach(t -> t.setQuestionId(question.getId()));
        }
        iCourseQuestionChoiceService.saveBatch(choices);
        return AjaxResult.success();
    }
}
