package com.joolun.web.controller.book;

import com.joolun.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书籍问题Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/question")
public class BookQuestionController extends BaseController {
//    @Autowired
//    private IBookQuestionService bookQuestionService;
//
//    /**
//     * 查询书籍问题列表
//     */
//    @GetMapping("/list")
//    public TableDataInfo list(BookQuestion bookQuestion) {
//        startPage();
//        List<BookQuestion> list = bookQuestionService.selectBookQuestionList(bookQuestion);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出书籍问题列表
//     */
//    @Log(title = "书籍问题", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(BookQuestion bookQuestion) {
//        List<BookQuestion> list = bookQuestionService.selectBookQuestionList(bookQuestion);
//        ExcelUtil<BookQuestion> util = new ExcelUtil<BookQuestion>(BookQuestion.class);
//        return util.exportExcel(list, "question");
//    }
//
//    /**
//     * 获取书籍问题详细信息
//     */
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(bookQuestionService.selectBookQuestionById(id));
//    }
//
//    /**
//     * 新增书籍问题
//     */
//    @Log(title = "书籍问题", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody BookQuestion bookQuestion) {
//        return toAjax(bookQuestionService.insertBookQuestion(bookQuestion));
//    }
//
//    /**
//     * 修改书籍问题
//     */
//    @Log(title = "书籍问题", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody BookQuestion bookQuestion) {
//        return toAjax(bookQuestionService.updateBookQuestion(bookQuestion));
//    }
//
//    /**
//     * 删除书籍问题
//     */
//    @Log(title = "书籍问题", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(bookQuestionService.deleteBookQuestionByIds(ids));
//    }
}
