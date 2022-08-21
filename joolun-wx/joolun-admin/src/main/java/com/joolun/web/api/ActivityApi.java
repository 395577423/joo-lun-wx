package com.joolun.web.api;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.page.TableDataInfo;
import com.joolun.mall.entity.Activity;
import com.joolun.mall.service.IActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-19
 */
@Slf4j
@RestController
@RequestMapping("/weixin/api/activity")
public class ActivityApi extends BaseController {

    @Autowired
    private IActivityService activityService;

    /**
     * 查询社会活动列表
     */
    @PostMapping("/list")
    public AjaxResult list(Page page, Activity activity) {
        page = activityService.page(page, Wrappers.lambdaQuery(activity));
        return AjaxResult.success(page);
    }


    /**
     * 查询课程关联的社会活动列表
     */
    @GetMapping("/listByCourseId")
    public AjaxResult list(Long courseId) {
        List<Activity> list = activityService.listByCourseId(courseId);
        return AjaxResult.success(list);
    }

    /**
     * 根据id查询活动详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable("id") Long id){
        return AjaxResult.success(activityService.getById(id));
    }

}
