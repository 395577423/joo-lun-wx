package com.joolun.web.controller.book;

import com.joolun.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 书籍故事Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/story")
public class BookStoryController extends BaseController {
//    @Autowired
//    private IBookStoryService bookStoryService;
//
//    /**
//     * 查询书籍故事列表
//     */
//    @GetMapping("/list")
//    public TableDataInfo list(BookStory bookStory) {
//        startPage();
//        List<BookStory> list = bookStoryService.selectBookStoryList(bookStory);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出书籍故事列表
//     */
//    @Log(title = "书籍故事", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(BookStory bookStory) {
//        List<BookStory> list = bookStoryService.selectBookStoryList(bookStory);
//        ExcelUtil<BookStory> util = new ExcelUtil<BookStory>(BookStory.class);
//        return util.exportExcel(list, "story");
//    }
//
//    /**
//     * 获取书籍故事详细信息
//     */
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(bookStoryService.selectBookStoryById(id));
//    }
//
//    /**
//     * 新增书籍故事
//     */
//    @Log(title = "书籍故事", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody BookStory bookStory) {
//        return toAjax(bookStoryService.insertBookStory(bookStory));
//    }
//
//    /**
//     * 修改书籍故事
//     */
//    @Log(title = "书籍故事", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody BookStory bookStory) {
//        return toAjax(bookStoryService.updateBookStory(bookStory));
//    }
//
//    /**
//     * 删除书籍故事
//     */
//    @Log(title = "书籍故事", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(bookStoryService.deleteBookStoryByIds(ids));
//    }
}
