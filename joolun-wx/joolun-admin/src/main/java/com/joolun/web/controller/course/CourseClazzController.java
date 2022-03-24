package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.SecurityUtils;
import com.joolun.mall.entity.CourseClazz;
import com.joolun.mall.entity.CourseVideo;
import com.joolun.mall.service.ICourseClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 课程视频Controller
 *
 * @author Owen
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course/clazz")
public class CourseClazzController extends BaseController {
    @Autowired
    private ICourseClazzService courseClazzService;

    /**
     * 查询课程视频列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, CourseClazz courseClazz) {
        return AjaxResult.success(courseClazzService.page(page, Wrappers.query(courseClazz)));
    }

    /**
     * 获取课程视频详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(courseClazzService.getById(id));
    }

    /**
     * 新增课程视频
     */
    @Log(title = "课程视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseClazz courseClazz) {
        courseClazz.setCreateBy(SecurityUtils.getUsername());
        courseClazz.setCreateTime(LocalDateTime.now());
        return AjaxResult.success(courseClazzService.save(courseClazz));
    }

    /**
     * 修改课程视频
     */
    @Log(title = "课程视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseClazz courseClazz) {
        return AjaxResult.success(courseClazzService.updateById(courseClazz));
    }

    /**
     * 删除课程视频
     */
    @Log(title = "课程视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(courseClazzService.removeByIds(Arrays.asList(ids)));
    }
}
