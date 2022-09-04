package com.joolun.web.controller.activity;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.common.core.page.TableDataInfo;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.SecurityUtils;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.mall.service.IUserShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author Lanjian
 * @date 2022-09-03
 */
@RestController
@RequestMapping("/mall/record")
public class UserShareRecordController extends BaseController {
    @Autowired
    private IUserShareRecordService userShareRecordService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('mall:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserShareRecord userShareRecord) {
        startPage();
        List<UserShareRecord> list = userShareRecordService.list(Wrappers.lambdaQuery(userShareRecord));
        return getDataTable(list);
    }


    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userShareRecordService.getById(id));
    }


    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('mall:record:edit')")
    @Log(title = "【请填写功能名称】" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserShareRecord userShareRecord) {
        return toAjax(userShareRecordService.save(userShareRecord) ? 1 : 0);
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('mall:record:remove')")
    @Log(title = "【请填写功能名称】" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userShareRecordService.removeByIds(Lists.newArrayList(ids)) ? 1 : 0);
    }
}
