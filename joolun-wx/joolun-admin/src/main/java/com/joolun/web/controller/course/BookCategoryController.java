/**
 * Copyright (C) 2018-2019
 * All rights reserved, Designed By www.joolun.com
 */
package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.entity.BookCategory;
import com.joolun.mall.service.IBookCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 书籍类目
 *
 * @author Owen
 * @date 2019-08-12 11:46:28
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bookcategory")
@Api(value = "bookcategory", tags = "书籍类目管理")
public class BookCategoryController extends BaseController {

    private final IBookCategoryService bookCategoryService;

    /**
     * 分页查询
     *
     * @param page          分页对象
     * @param bookCategory 书籍类目
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public AjaxResult getGoodsCategoryPage(Page page, BookCategory bookCategory) {
        return AjaxResult.success(bookCategoryService.page(page, Wrappers.query(bookCategory)));
    }

    /**
     * 返回树形集合
     *
     * @return
     */
    @ApiOperation(value = "返回树形集合")
    @GetMapping("/tree")
    public AjaxResult getGoodsCategoryTree() {
        return AjaxResult.success(bookCategoryService.selectTree(null));
    }

    /**
     * 通过id查询书籍类目
     *
     * @param id
     * @return R
     */
    @ApiOperation(value = "通过id查询书籍类目")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") String id) {
        return AjaxResult.success(bookCategoryService.getById(id));
    }

    /**
     * 新增书籍类目
     *
     * @param bookCategory 书籍类目
     * @return R
     */
    @ApiOperation(value = "新增书籍类目")
    @PostMapping
    public AjaxResult save(@RequestBody BookCategory bookCategory) {
        return AjaxResult.success(bookCategoryService.save(bookCategory));
    }

    /**
     * 修改书籍类目
     *
     * @param bookCategory 书籍类目
     * @return R
     */
    @ApiOperation(value = "修改书籍类目")
    @PutMapping
    public AjaxResult updateById(@RequestBody BookCategory bookCategory) {
        if (bookCategory.getId().equals(bookCategory.getParentId())) {
            return AjaxResult.error("不能将本级设为父类");
        }
        return AjaxResult.success(bookCategoryService.updateById(bookCategory));
    }

    /**
     * 通过id删除书籍类目
     *
     * @param id
     * @return R
     */
    @ApiOperation(value = "通过id删除书籍类目")
    @DeleteMapping("/{id}")
    public AjaxResult removeById(@PathVariable String id) {
        return AjaxResult.success(bookCategoryService.removeById(id));
    }

}
