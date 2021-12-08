package com.joolun.web.controller.course;

import com.joolun.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程视频Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/system/video")
public class CourseVideoController extends BaseController {
//    @Autowired
//    private ICourseVideoService courseVideoService;
//
//    /**
//     * 查询课程视频列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:video:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(CourseVideo courseVideo) {
//        startPage();
//        List<CourseVideo> list = courseVideoService.selectCourseVideoList(courseVideo);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出课程视频列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:video:export')")
//    @Log(title = "课程视频", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(CourseVideo courseVideo) {
//        List<CourseVideo> list = courseVideoService.selectCourseVideoList(courseVideo);
//        ExcelUtil<CourseVideo> util = new ExcelUtil<CourseVideo>(CourseVideo.class);
//        return util.exportExcel(list, "video");
//    }
//
//    /**
//     * 获取课程视频详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:video:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(courseVideoService.selectCourseVideoById(id));
//    }
//
//    /**
//     * 新增课程视频
//     */
//    @PreAuthorize("@ss.hasPermi('system:video:add')")
//    @Log(title = "课程视频", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody CourseVideo courseVideo) {
//        return toAjax(courseVideoService.insertCourseVideo(courseVideo));
//    }
//
//    /**
//     * 修改课程视频
//     */
//    @PreAuthorize("@ss.hasPermi('system:video:edit')")
//    @Log(title = "课程视频", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody CourseVideo courseVideo) {
//        return toAjax(courseVideoService.updateCourseVideo(courseVideo));
//    }
//
//    /**
//     * 删除课程视频
//     */
//    @PreAuthorize("@ss.hasPermi('system:video:remove')")
//    @Log(title = "课程视频", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(courseVideoService.deleteCourseVideoByIds(ids));
//    }
}
