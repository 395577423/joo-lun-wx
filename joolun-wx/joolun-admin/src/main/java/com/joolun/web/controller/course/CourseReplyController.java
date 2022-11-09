package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.entity.CourseReply;
import com.joolun.mall.service.ICourseReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程音频回复Controller
 *
 * @author Lanjian
 * @date 2022-11-08
 */
@RestController
@RequestMapping("/course/reply")
public class CourseReplyController extends BaseController {
    @Autowired
    private ICourseReplyService courseReplyService;

    /**
     * 查询课程音频回复列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, CourseReply courseReply) {
        return AjaxResult.success(courseReplyService.page(page, Wrappers.lambdaQuery(courseReply)));
    }

    /**
     * 导出课程音频回复列表
     */
    @Log(title = "课程音频回复", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CourseReply courseReply) {
        List<CourseReply> list = courseReplyService.list(Wrappers.lambdaQuery(courseReply));
        ExcelUtil<CourseReply> util = new ExcelUtil<CourseReply>(CourseReply.class);
        return util.exportExcel(list, "reply");
    }

    /**
     * 获取课程音频回复详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(courseReplyService.getById(id));
    }

    /**
     * 新增课程音频回复
     */
    @Log(title = "课程音频回复", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseReply courseReply) {
        return toAjax(courseReplyService.save(courseReply) ? 1 : 0);
    }

    /**
     * 修改课程音频回复
     */
    @Log(title = "课程音频回复", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseReply courseReply) {
        boolean result = courseReplyService.updateById(courseReply);
        return toAjax(result ? 1 : 0);
    }

    /**
     * 删除课程音频回复
     */
    @Log(title = "课程音频回复", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(courseReplyService.removeByIds(Lists.newArrayList(ids)) ? 1 : 0);
    }
}
