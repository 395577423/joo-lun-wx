package com.joolun.web.controller.activity;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.common.core.page.TableDataInfo;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.SecurityUtils;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.dto.ActivityRelateCourseDto;
import com.joolun.mall.entity.Activity;
import com.joolun.mall.entity.Course;
import com.joolun.mall.service.IActivityRelatedCourseService;
import com.joolun.mall.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 社会活动Controller
 *
 * @author Owen
 * @date 2022-08-12
 */
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController {
    @Autowired
    private IActivityService activityService;

    @Autowired
    private IActivityRelatedCourseService activityRelatedCourseService;

    /**
     * 查询社会活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(Activity activity) {
        startPage();
        List<Activity> list = activityService.list(Wrappers.lambdaQuery(activity).orderByDesc(Activity::getCreateTime));
        return getDataTable(list);
    }

    /**
     * 导出社会活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:activity:export')")
    @Log(title = "社会活动", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Activity activity) {
        List<Activity> list = activityService.list(Wrappers.lambdaQuery(activity));
        ExcelUtil<Activity> util = new ExcelUtil<Activity>(Activity.class);
        return util.exportExcel(list, "activity");
    }

    /**
     * 获取社会活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(activityService.getById(id));
    }

    /**
     * 新增社会活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:add')")
    @Log(title = "社会活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Activity activity) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        activity.setPublished(false);
        activity.setCreator(user.getUserName());
        activity.setCreatorId(user.getUserId());
        boolean save = activityService.save(activity);
        return toAjax(save ? 1 : 0);
    }

    /**
     * 修改社会活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:edit')")
    @Log(title = "社会活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Activity activity) {
        //发布是校验是否已关联课程
        if (activity.getPublished()) {
            List<Course> relateCourse = activityRelatedCourseService.getRelateCourse(activity.getId());
            if (CollectionUtil.isEmpty(relateCourse)) {
                return AjaxResult.error("发布前请先关联课程！");
            }
        }
        boolean success = activityService.updateById(activity);
        return toAjax(success ? 1 : 0);
    }

    /**
     * 删除社会活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:remove')")
    @Log(title = "社会活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(activityService.removeByIds(Lists.newArrayList(ids)) ? 1 : 0);
    }

    /**
     * 活动关联课程
     *
     * @param activityRelateCourseDto
     * @return
     */
    @PostMapping("/relate/course")
    @Log(title = "活动关联课程", businessType = BusinessType.OTHER)
    public AjaxResult relateCourse(@RequestBody ActivityRelateCourseDto activityRelateCourseDto) {
        activityService.doRelateCourse(activityRelateCourseDto);
        return AjaxResult.success();
    }

    @GetMapping("/relate/course/{activityId}")
    public AjaxResult getRelateCourse(@PathVariable("activityId") Long activityId) {

        return AjaxResult.success(activityRelatedCourseService.getRelateCourse(activityId));
    }
}
