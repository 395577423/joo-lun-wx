package com.joolun.web.controller.book;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.CourseStory;
import com.joolun.mall.service.ICourseStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 书籍故事Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/book/story")
public class BookStoryController extends BaseController {
    @Autowired
    private ICourseStoryService bookStoryService;

    /**
     * 查询书籍故事列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, CourseStory bookStory) {
        return AjaxResult.success(bookStoryService.page(page, Wrappers.query(bookStory)));
    }


    /**
     * 获取书籍故事详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookStoryService.getById(id));
    }

    /**
     * 新增书籍故事
     */
    @Log(title = "书籍故事", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseStory bookStory) {
        return AjaxResult.success(bookStoryService.save(bookStory));
    }

    /**
     * 修改书籍故事
     */
    @Log(title = "书籍故事", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseStory bookStory) {
        return AjaxResult.success(bookStoryService.updateById(bookStory));
    }

    /**
     * 删除书籍故事
     */
    @Log(title = "书籍故事", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(bookStoryService.removeByIds(Arrays.asList(ids)));
    }
}
