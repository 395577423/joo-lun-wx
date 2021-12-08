package com.joolun.web.controller.course;

import com.joolun.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/course")
public class CourseController extends BaseController {
//    @Autowired
//    private ICourseService courseService;
//
//    /**
//     * 查询课程列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(Course course) {
//        startPage();
//        List<Course> list = courseService.selectCourseList(course);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出课程列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:export')")
//    @Log(title = "课程", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(Course course) {
//        List<Course> list = courseService.selectCourseList(course);
//        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
//        return util.exportExcel(list, "course");
//    }
//
//    /**
//     * 获取课程详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(courseService.selectCourseById(id));
//    }
//
//    /**
//     * 新增课程
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:add')")
//    @Log(title = "课程", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody Course course) {
//        return toAjax(courseService.insertCourse(course));
//    }
//
//    /**
//     * 修改课程
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:edit')")
//    @Log(title = "课程", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody Course course) {
//        return toAjax(courseService.updateCourse(course));
//    }
//
//    /**
//     * 删除课程
//     */
//    @PreAuthorize("@ss.hasPermi('system:course:remove')")
//    @Log(title = "课程", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(courseService.deleteCourseByIds(ids));
//    }
}
