package com.joolun.web.controller.course;

import com.joolun.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户购买课程Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/course")
public class UserCourseController extends BaseController {
//    @Autowired
//    private IUserCourseService userCourseService;
//
//    /**
//     * 查询用户购买课程列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(UserCourse userCourse) {
//        startPage();
//        List<UserCourse> list = userCourseService.selectUserCourseList(userCourse);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出用户购买课程列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:export')")
//    @Log(title = "用户购买课程", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(UserCourse userCourse) {
//        List<UserCourse> list = userCourseService.selectUserCourseList(userCourse);
//        ExcelUtil<UserCourse> util = new ExcelUtil<UserCourse>(UserCourse.class);
//        return util.exportExcel(list, "course");
//    }
//
//    /**
//     * 获取用户购买课程详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(userCourseService.selectUserCourseById(id));
//    }
//
//    /**
//     * 新增用户购买课程
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:add')")
//    @Log(title = "用户购买课程", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody UserCourse userCourse) {
//        return toAjax(userCourseService.insertUserCourse(userCourse));
//    }
//
//    /**
//     * 修改用户购买课程
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:edit')")
//    @Log(title = "用户购买课程", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody UserCourse userCourse) {
//        return toAjax(userCourseService.updateUserCourse(userCourse));
//    }
//
//    /**
//     * 删除用户购买课程
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:remove')")
//    @Log(title = "用户购买课程", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(userCourseService.deleteUserCourseByIds(ids));
//    }
}
