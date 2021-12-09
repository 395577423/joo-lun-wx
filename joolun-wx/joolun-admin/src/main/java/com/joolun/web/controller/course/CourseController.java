package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.Course;
import com.joolun.mall.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 课程Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {
    @Autowired
    private ICourseService courseService;

    /**
     * 查询课程列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, Course course) {
        return AjaxResult.success(courseService.page(page, Wrappers.query(course)));
    }

    /**
     * 获取课程详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(courseService.getById(id));
    }

    /**
     * 新增课程
     */
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course) {
        return AjaxResult.success(courseService.save(course));
    }

    /**
     * 修改课程
     */
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course course) {
        return AjaxResult.success(courseService.updateById(course));
    }

    /**
     * 删除课程
     */
    @Log(title = "课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(courseService.removeByIds(Arrays.asList(ids)));
    }
}
