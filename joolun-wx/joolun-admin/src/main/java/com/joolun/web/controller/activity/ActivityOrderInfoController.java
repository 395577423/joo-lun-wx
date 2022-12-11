package com.joolun.web.controller.activity;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Lists;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.entity.ActivityPerson;
import com.joolun.mall.service.IActivityOrderInfoService;
import com.joolun.mall.service.IActivityOrderPersonService;
import com.joolun.mall.service.IActivityPersonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;

import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.common.core.page.TableDataInfo;

/**
 * 订单Controller
 *
 * @author Lanjian
 * @date 2022-09-08
 */
@RestController
@RequestMapping("/activity/order")
public class ActivityOrderInfoController extends BaseController {
    @Autowired
    private IActivityOrderInfoService activityOrderInfoService;

    @Autowired
    private IActivityPersonService activityPersonService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('activity:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityOrderInfo activityOrderInfo) {
        startPage();
        List<ActivityOrderInfo> list = activityOrderInfoService.list(Wrappers.lambdaQuery(activityOrderInfo)
                .orderByDesc(ActivityOrderInfo::getCreateTime));
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('activity:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ActivityOrderInfo activityOrderInfo) {
        List<ActivityOrderInfo> list = activityOrderInfoService.list(Wrappers.lambdaQuery(activityOrderInfo));
        ExcelUtil<ActivityOrderInfo> util = new ExcelUtil<ActivityOrderInfo>(ActivityOrderInfo.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('activity:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(activityOrderInfoService.getById(id));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('activity:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityOrderInfo activityOrderInfo) {
        boolean result = activityOrderInfoService.save(activityOrderInfo);
        return toAjax(result ? 1 : 0);
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('activity:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityOrderInfo activityOrderInfo) {
        boolean result = activityOrderInfoService.save(activityOrderInfo);
        return toAjax(result ? 1 : 0);
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('activity:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        boolean result = activityOrderInfoService.removeByIds(Lists.newArrayList(ids));
        return toAjax(result ? 1 : 0);
    }


    /**
     * 获取订单出行人信息
     * @param activityOrderId
     * @return
     */
    @GetMapping("/getPersons")
    public AjaxResult getOrderPersons(String activityOrderId){
        List<ActivityPerson> activityOrderPersons = activityPersonService.getActivityOrderPersons(activityOrderId);
        return AjaxResult.success(activityOrderPersons);
    }


    @GetMapping("/complete")
    public AjaxResult complete(String id) {

        return activityOrderInfoService.completed(id);
    }
}
