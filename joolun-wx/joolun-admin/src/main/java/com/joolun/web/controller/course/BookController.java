package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.domain.entity.SysDictData;
import com.joolun.common.core.domain.model.LoginUser;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.ServletUtils;
import com.joolun.common.utils.StringUtils;
import com.joolun.framework.web.service.TokenService;
import com.joolun.mall.entity.Book;
import com.joolun.mall.service.IBookService;
import com.joolun.system.service.ISysDictDataService;
import com.joolun.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 书籍Controller
 *
 * @author Owen
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course/book")
public class BookController extends BaseController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private TokenService tokenService;
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
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        book.setCreateBy(loginUser.getUsername());
        book.setUpdateBy(loginUser.getUsername());
        return AjaxResult.success(bookService.save(book));
    }

    /**
     * 修改书籍
     */
    @Log(title = "书籍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Book book) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        book.setCreateBy(loginUser.getUsername());
        book.setUpdateBy(loginUser.getUsername());
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

    @GetMapping("/type")
    public AjaxResult getType() {
        SysDictData dictData = new SysDictData();
        dictData.setDictType("books_type");
        List<SysDictData> sysDictData = dictDataService.selectDictDataList(dictData);
        return AjaxResult.success(sysDictData);
    }

    @GetMapping("/list")
    public AjaxResult getBookList(String name){

        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(name)) {
            wrapper.like("title", name);
        }
        return AjaxResult.success(bookService.list(wrapper));
    }
}
