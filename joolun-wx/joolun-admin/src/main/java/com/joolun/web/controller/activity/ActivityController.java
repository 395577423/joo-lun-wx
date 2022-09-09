package com.joolun.web.controller.activity;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import com.joolun.mall.dto.ActivityDto;
import com.joolun.mall.dto.ActivityRelateCourseDto;
import com.joolun.mall.entity.Activity;
import com.joolun.mall.entity.ActivityPriceCase;
import com.joolun.mall.entity.Course;
import com.joolun.mall.service.ActivityPriceCaseService;
import com.joolun.mall.service.IActivityRelatedCourseService;
import com.joolun.mall.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    private ActivityPriceCaseService activityPriceCaseService;

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
        Activity activity = activityService.getById(id);
        LambdaQueryWrapper<ActivityPriceCase> queryWrapper = Wrappers.<ActivityPriceCase>lambdaQuery()
                .eq(ActivityPriceCase::getActivityId, id);
        List<ActivityPriceCase> activityPriceCases = activityPriceCaseService.list(queryWrapper);
        List<Course> relateCourse = activityRelatedCourseService.getRelateCourse(id);
        ActivityDto activityDto = new ActivityDto();
        activityDto.setActivity(activity);
        activityDto.setPriceCases(activityPriceCases);
        activityDto.setCourses(relateCourse);
        return AjaxResult.success(activityDto);
    }

    /**
     * 新增社会活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:add')")
    @Log(title = "社会活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityDto activityDto) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        int add = activityService.add(activityDto, user);

        return toAjax(add);
    }

    /**
     * 修改社会活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:edit')")
    @Log(title = "社会活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityDto activityDto) {
        //发布是校验是否已关联课程
        Activity activity = activityDto.getActivity();
        if (activity.getPublished()) {
            List<Course> relateCourse = activityRelatedCourseService.getRelateCourse(activity.getId());
            if (CollectionUtil.isEmpty(relateCourse)) {
                return AjaxResult.error("发布前请先关联课程！");
            }
        }
        int success = activityService.edit(activityDto);
        return toAjax(success);
    }

    /**
     * 删除社会活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:remove')")
    @Log(title = "社会活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            activityPriceCaseService.remove(Wrappers.<ActivityPriceCase>lambdaQuery().eq(ActivityPriceCase::getActivityId, id));
        }
        int result = activityService.removeByIds(Lists.newArrayList(ids)) ? 1 : 0;
        return toAjax(result);
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

        return AjaxResult.success();
    }

    /**
     * 获取活动套餐信息
     *
     * @param activityId
     * @return
     */
    @GetMapping("/price/case/list")
    public AjaxResult getPriceCase(Long activityId) {
        LambdaQueryWrapper<ActivityPriceCase> queryWrapper = Wrappers.<ActivityPriceCase>lambdaQuery()
                .eq(ActivityPriceCase::getActivityId, activityId);
        List<ActivityPriceCase> activityPriceCases = activityPriceCaseService.list(queryWrapper);
        return AjaxResult.success(activityPriceCases);
    }


    /**
     * 修改社会活动
     */
    @PreAuthorize("@ss.hasPermi('system:activity:edit')")
    @Log(title = "社会活动", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/publish")
    public AjaxResult publish(@RequestBody Activity activity) {
        //发布是校验是否已关联课程
        if (activity.getPublished()) {
            List<Course> relateCourse = activityRelatedCourseService.getRelateCourse(activity.getId());
            if (CollectionUtil.isEmpty(relateCourse)) {
                return AjaxResult.error("发布前请先关联课程！");
            }
        }
        LambdaUpdateWrapper<Activity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(Activity::getPublished, activity.getPublished());
        updateWrapper.eq(Activity::getId, activity.getId());
        boolean update = activityService.update(updateWrapper);
        return toAjax(update ? 1 : 0);
    }
}
