package com.joolun.web.controller.book;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.Book;
import com.joolun.mall.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 书籍Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/book")
public class BookController extends BaseController {
    @Autowired
    private IBookService bookService;

    /**
     * 查询书籍列表
     */
    @GetMapping("/page")
    public AjaxResult getGoodsSpuPage(Page page, Book book) {
        return AjaxResult.success(bookService.page(page, Wrappers.query(book)));
    }

    /**
     * 获取书籍详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bookService.getById(id));
    }

    /**
     * 新增书籍
     */
    @Log(title = "书籍", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Book book) {
        return AjaxResult.success(bookService.save(book));
    }

    /**
     * 修改书籍
     */
    @Log(title = "书籍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Book book) {
        return AjaxResult.success(bookService.updateById(book));
    }

    /**
     * 删除书籍
     */
    @Log(title = "书籍", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(bookService.removeByIds(Arrays.asList(ids)));
    }
}
