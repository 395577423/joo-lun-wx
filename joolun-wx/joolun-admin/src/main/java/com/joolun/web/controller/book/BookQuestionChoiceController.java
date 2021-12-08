package com.joolun.web.controller.book;

import com.joolun.common.core.controller.BaseController;
import com.joolun.mall.service.IBookQuestionChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书籍问题选项Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/choice")
public class BookQuestionChoiceController extends BaseController {
    @Autowired
    private IBookQuestionChoiceService bookQuestionChoiceService;

//    /**
//     * 查询书籍问题选项列表
//     */
//    @GetMapping("/list")
//    public TableDataInfo list(BookQuestionChoice bookQuestionChoice) {
//        startPage();
//        List<BookQuestionChoice> list = bookQuestionChoiceService.selectBookQuestionChoiceList(bookQuestionChoice);
//        return getDataTable(list);
//    }
//
//    /**
//     * 获取书籍问题选项详细信息
//     */
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(bookQuestionChoiceService.selectBookQuestionChoiceById(id));
//    }
//
//    /**
//     * 新增书籍问题选项
//     */
//    @Log(title = "书籍问题选项", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody BookQuestionChoice bookQuestionChoice) {
//        return toAjax(bookQuestionChoiceService.insertBookQuestionChoice(bookQuestionChoice));
//    }
//
//    /**
//     * 修改书籍问题选项
//     */
//    @Log(title = "书籍问题选项", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody BookQuestionChoice bookQuestionChoice) {
//        return toAjax(bookQuestionChoiceService.updateBookQuestionChoice(bookQuestionChoice));
//    }
//
//    /**
//     * 删除书籍问题选项
//     */
//    @Log(title = "书籍问题选项", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(bookQuestionChoiceService.deleteBookQuestionChoiceByIds(ids));
//    }
}
