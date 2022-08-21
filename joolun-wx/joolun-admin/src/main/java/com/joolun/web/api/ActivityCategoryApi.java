package com.joolun.web.api;

import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.entity.ActivityCategory;
import com.joolun.mall.entity.GoodsCategory;
import com.joolun.mall.service.IActivityCategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-19
 */
@Slf4j
@RestController
@RequestMapping("/weixin/api/activity/category")
public class ActivityCategoryApi {

    @Autowired
    private IActivityCategoryService activityCategoryService;

    /**
     * 返回list
     */
    @ApiOperation(value = "返回活动分类列表")
    @GetMapping("/list")
    public AjaxResult activityCategoryList(ActivityCategory activityCategory) {
        return AjaxResult.success(activityCategoryService.selectActivityCategoryList(activityCategory));
    }

}
