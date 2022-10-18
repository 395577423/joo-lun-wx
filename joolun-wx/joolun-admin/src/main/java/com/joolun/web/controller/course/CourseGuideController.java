package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.entity.CourseGuide;
import com.joolun.mall.service.ICourseGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程导读Controller
 *
 * @author Lanjian
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/course/guide")
public class CourseGuideController extends BaseController {
    @Autowired
    private ICourseGuideService courseGuideService;

    /**
     * 查询课程导读列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, CourseGuide courseGuide) {
        return AjaxResult.success(courseGuideService.page(page, Wrappers.query(courseGuide)));
    }

    /**
     * 导出课程导读列表
     */
    @Log(title = "课程导读", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CourseGuide courseGuide) {
        List<CourseGuide> list = courseGuideService.list(Wrappers.lambdaQuery(courseGuide));
        ExcelUtil<CourseGuide> util = new ExcelUtil<CourseGuide>(CourseGuide.class);
        return util.exportExcel(list, "guide");
    }

    /**
     * 获取课程导读详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(courseGuideService.getById(id));
    }

    /**
     * 新增课程导读
     */
    @Log(title = "课程导读", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseGuide courseGuide) {
        return toAjax(courseGuideService.save(courseGuide) ? 1 : 0);
    }

    /**
     * 修改课程导读
     */
    @Log(title = "课程导读", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseGuide courseGuide) {
        boolean result = courseGuideService.save(courseGuide);
        return toAjax(result ? 1 : 0);
    }

    /**
     * 删除课程导读
     */
    @Log(title = "课程导读", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(courseGuideService.removeByIds(Lists.newArrayList(ids)) ? 1 : 0);
    }
}
