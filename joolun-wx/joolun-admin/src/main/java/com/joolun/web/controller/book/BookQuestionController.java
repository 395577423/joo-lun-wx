package com.joolun.web.controller.book;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.CourseQuestion;
import com.joolun.mall.service.ICourseQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 书籍问题Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/book/question")
public class BookQuestionController extends BaseController {
    @Autowired
    private ICourseQuestionService bookQuestionService;

    /**
     * 查询书籍问题列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, CourseQuestion bookQuestion) {
        return AjaxResult.success(bookQuestionService.page(page, Wrappers.query(bookQuestion)));
    }

    /**
     * 获取书籍问题详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookQuestionService.getById(id));
    }

    /**
     * 新增书籍问题
     */
    @Log(title = "书籍问题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseQuestion bookQuestion) {
        return AjaxResult.success(bookQuestionService.save(bookQuestion));
    }

    /**
     * 修改书籍问题
     */
    @Log(title = "书籍问题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseQuestion bookQuestion) {
        return AjaxResult.success(bookQuestionService.updateById(bookQuestion));
    }

    /**
     * 删除书籍问题
     */
    @Log(title = "书籍问题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(bookQuestionService.removeByIds(Arrays.asList(ids)));
    }
}
