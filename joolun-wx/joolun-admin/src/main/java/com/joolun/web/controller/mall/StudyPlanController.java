package com.joolun.web.controller.mall;

import java.util.List;

import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.mall.entity.StudyPlan;
import com.joolun.mall.service.IStudyPlanService;
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
import com.joolun.common.core.page.TableDataInfo;

/**
 * 学习奖励计划Controller
 * 
 * @author www.joolun.com
 * @date 2021-11-30
 */
@RestController
@RequestMapping("/mall/plan")
public class StudyPlanController extends BaseController
{
    @Autowired
    private IStudyPlanService studyPlanService;

    /**
     * 查询学习奖励计划列表
     */
    @PreAuthorize("@ss.hasPermi('system:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudyPlan studyPlan)
    {
        startPage();
        List<StudyPlan> list = studyPlanService.selectStudyPlanList(studyPlan);
        return getDataTable(list);
    }

    /**
     * 导出学习奖励计划列表
     */
    @PreAuthorize("@ss.hasPermi('system:plan:export')")
    @Log(title = "学习奖励计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(StudyPlan studyPlan)
    {
        List<StudyPlan> list = studyPlanService.selectStudyPlanList(studyPlan);
        ExcelUtil<StudyPlan> util = new ExcelUtil<StudyPlan>(StudyPlan.class);
        return util.exportExcel(list, "plan");
    }

    /**
     * 获取学习奖励计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:plan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(studyPlanService.selectStudyPlanById(id));
    }

    /**
     * 新增学习奖励计划
     */
    @PreAuthorize("@ss.hasPermi('system:plan:add')")
    @Log(title = "学习奖励计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StudyPlan studyPlan)
    {
        return toAjax(studyPlanService.insertStudyPlan(studyPlan));
    }

    /**
     * 修改学习奖励计划
     */
    @PreAuthorize("@ss.hasPermi('system:plan:edit')")
    @Log(title = "学习奖励计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StudyPlan studyPlan)
    {
        return toAjax(studyPlanService.updateStudyPlan(studyPlan));
    }

    /**
     * 删除学习奖励计划
     */
    @PreAuthorize("@ss.hasPermi('system:plan:remove')")
    @Log(title = "学习奖励计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(studyPlanService.deleteStudyPlanByIds(ids));
    }
}
