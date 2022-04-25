package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.StringUtils;
import com.joolun.mall.dto.CourseQuestionDto;
import com.joolun.mall.entity.CourseQuestion;
import com.joolun.mall.service.ICourseQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    /**
     * 查询书籍问题列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, CourseQuestion courseQuestion) {
        return AjaxResult.success(courseQuestionService.page(page, Wrappers.query(courseQuestion)));
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
        BeanUtils.copyProperties(courseQuestion,question);
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
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(courseQuestionService.removeByIds(Arrays.asList(ids)));
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
}
