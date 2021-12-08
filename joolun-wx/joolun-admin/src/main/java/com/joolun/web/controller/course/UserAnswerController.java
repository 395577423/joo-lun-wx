package com.joolun.web.controller.course;

import com.joolun.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户答案Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/answer")
public class UserAnswerController extends BaseController {
//    @Autowired
//    private IUserAnswerService userAnswerService;
//
//    /**
//     * 查询用户答案列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:answer:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(UserAnswer userAnswer) {
//        startPage();
//        List<UserAnswer> list = userAnswerService.selectUserAnswerList(userAnswer);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出用户答案列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:answer:export')")
//    @Log(title = "用户答案", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(UserAnswer userAnswer) {
//        List<UserAnswer> list = userAnswerService.selectUserAnswerList(userAnswer);
//        ExcelUtil<UserAnswer> util = new ExcelUtil<UserAnswer>(UserAnswer.class);
//        return util.exportExcel(list, "answer");
//    }
//
//    /**
//     * 获取用户答案详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:answer:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(userAnswerService.selectUserAnswerById(id));
//    }
//
//    /**
//     * 新增用户答案
//     */
//    @PreAuthorize("@ss.hasPermi('system:answer:add')")
//    @Log(title = "用户答案", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody UserAnswer userAnswer) {
//        return toAjax(userAnswerService.insertUserAnswer(userAnswer));
//    }
//
//    /**
//     * 修改用户答案
//     */
//    @PreAuthorize("@ss.hasPermi('system:answer:edit')")
//    @Log(title = "用户答案", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody UserAnswer userAnswer) {
//        return toAjax(userAnswerService.updateUserAnswer(userAnswer));
//    }
//
//    /**
//     * 删除用户答案
//     */
//    @PreAuthorize("@ss.hasPermi('system:answer:remove')")
//    @Log(title = "用户答案", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(userAnswerService.deleteUserAnswerByIds(ids));
//    }
}
