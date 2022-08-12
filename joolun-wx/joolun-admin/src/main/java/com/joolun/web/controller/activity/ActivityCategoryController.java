package com.joolun.web.controller.activity;

import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.page.TableDataInfo;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.entity.ActivityCategory;
import com.joolun.mall.service.IActivityCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author Owen
 * @date 2022-08-12
 */
@RestController
@RequestMapping("/activity/category")
public class ActivityCategoryController extends BaseController
{
    @Autowired
    private IActivityCategoryService activityCategoryService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityCategory activityCategory)
    {
        startPage();
        List<ActivityCategory> list = activityCategoryService.selectActivityCategoryList(activityCategory);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:category:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ActivityCategory activityCategory)
    {
        List<ActivityCategory> list = activityCategoryService.selectActivityCategoryList(activityCategory);
        ExcelUtil<ActivityCategory> util = new ExcelUtil<ActivityCategory>(ActivityCategory.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(activityCategoryService.selectActivityCategoryById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:category:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityCategory activityCategory)
    {
        return toAjax(activityCategoryService.insertActivityCategory(activityCategory));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:category:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityCategory activityCategory)
    {
        return toAjax(activityCategoryService.updateActivityCategory(activityCategory));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:category:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(activityCategoryService.deleteActivityCategoryByIds(ids));
    }
}
