package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.CourseQuestionChoice;
import com.joolun.mall.service.ICourseQuestionChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 书籍问题选项Controller
 *
 * @author Owen
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course/choice")
public class CourseQuestionChoiceController extends BaseController {
    @Autowired
    private ICourseQuestionChoiceService bookQuestionChoiceService;

    /**
     * 查询书籍问题选项列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, CourseQuestionChoice courseQuestionChoice) {
        return AjaxResult.success(bookQuestionChoiceService.page(page, Wrappers.query(courseQuestionChoice)));
    }

    /**
     * 获取书籍问题选项详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookQuestionChoiceService.getById(id));
    }

    /**
     * 新增书籍问题选项
     */
    @Log(title = "书籍问题选项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseQuestionChoice bookQuestionChoice) {
        return AjaxResult.success(bookQuestionChoiceService.save(bookQuestionChoice));
    }

    /**
     * 修改书籍问题选项
     */
    @Log(title = "书籍问题选项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseQuestionChoice bookQuestionChoice) {
        return AjaxResult.success(bookQuestionChoiceService.updateById(bookQuestionChoice));
    }

    /**
     * 删除书籍问题选项
     */
    @Log(title = "书籍问题选项", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(bookQuestionChoiceService.removeByIds(Arrays.asList(ids)));
    }

    @GetMapping("/list/{id}")
    public AjaxResult listByQuestion(@PathVariable Long id) {
        QueryWrapper<CourseQuestionChoice> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", id);
        return AjaxResult.success(bookQuestionChoiceService.list(wrapper));
    }

}
