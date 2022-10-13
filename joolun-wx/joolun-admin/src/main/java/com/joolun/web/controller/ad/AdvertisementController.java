package com.joolun.web.controller.ad;

import java.util.List;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Lists;
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
import com.joolun.mall.entity.Advertisement;
import com.joolun.mall.service.IAdvertisementService;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.common.core.page.TableDataInfo;

/**
 * 广告Controller
 * 
 * @author Lanjian
 * @date 2022-10-10
 */
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController extends BaseController
{
    @Autowired
    private IAdvertisementService advertisementService;

    /**
     * 查询广告列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Advertisement advertisement)
    {
        startPage();
        List<Advertisement> list = advertisementService.list(Wrappers.lambdaQuery(advertisement));
        return getDataTable(list);
    }

    /**
     * 导出广告列表
     */
    @Log(title = "广告", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Advertisement advertisement)
    {
        List<Advertisement> list = advertisementService.list(Wrappers.lambdaQuery(advertisement));
        ExcelUtil<Advertisement> util = new ExcelUtil<Advertisement>(Advertisement.class);
        return util.exportExcel(list, "advertisement");
    }

    /**
     * 获取广告详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(advertisementService.getById(id));
    }

    /**
     * 新增广告
     */
    @Log(title = "广告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Advertisement advertisement)
    {
        return toAjax(advertisementService.save(advertisement)?1:0);
    }

    /**
     * 修改广告
     */
    @Log(title = "广告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Advertisement advertisement)
    {
        boolean result = advertisementService.updateById(advertisement);
        return toAjax(result?1:0);
    }

    /**
     * 删除广告
     */
    @Log(title = "广告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(advertisementService.removeByIds(Lists.newArrayList(ids))?1:0);
    }
}
