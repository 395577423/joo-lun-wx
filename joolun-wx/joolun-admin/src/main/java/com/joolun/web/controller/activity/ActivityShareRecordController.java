package com.joolun.web.controller.activity;

import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.page.TableDataInfo;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.entity.ActivityShareRecord;
import com.joolun.mall.service.IActivityShareRecordService;
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
@RequestMapping("/activity/share/record")
public class ActivityShareRecordController extends BaseController
{
    @Autowired
    private IActivityShareRecordService activityShareRecordService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityShareRecord activityShareRecord)
    {
        startPage();
        List<ActivityShareRecord> list = activityShareRecordService.selectActivityShareRecordList(activityShareRecord);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:record:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ActivityShareRecord activityShareRecord)
    {
        List<ActivityShareRecord> list = activityShareRecordService.selectActivityShareRecordList(activityShareRecord);
        ExcelUtil<ActivityShareRecord> util = new ExcelUtil<ActivityShareRecord>(ActivityShareRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(activityShareRecordService.selectActivityShareRecordById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:record:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityShareRecord activityShareRecord)
    {
        return toAjax(activityShareRecordService.insertActivityShareRecord(activityShareRecord));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:record:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityShareRecord activityShareRecord)
    {
        return toAjax(activityShareRecordService.updateActivityShareRecord(activityShareRecord));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:record:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(activityShareRecordService.deleteActivityShareRecordByIds(ids));
    }
}
