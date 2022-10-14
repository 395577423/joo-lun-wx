package com.joolun.web.controller.propaganda;

import java.util.List;
import java.util.Objects;

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
import com.joolun.mall.entity.Propaganda;
import com.joolun.mall.service.IPropagandaService;
import com.joolun.common.utils.poi.ExcelUtil;
import com.joolun.common.core.page.TableDataInfo;

/**
 * 宣传视频Controller
 *
 * @author Lanjian
 * @date 2022-10-13
 */
@RestController
@RequestMapping("/propaganda")
public class PropagandaController extends BaseController {
    @Autowired
    private IPropagandaService propagandaService;

    /**
     * 查询宣传视频列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Propaganda propaganda) {
        startPage();
        List<Propaganda> list = propagandaService.list(Wrappers.lambdaQuery(propaganda));
        return getDataTable(list);
    }

    /**
     * 导出宣传视频列表
     */
    @Log(title = "宣传视频", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Propaganda propaganda) {
        List<Propaganda> list = propagandaService.list(Wrappers.lambdaQuery(propaganda));
        ExcelUtil<Propaganda> util = new ExcelUtil<Propaganda>(Propaganda.class);
        return util.exportExcel(list, "propaganda");
    }

    /**
     * 获取宣传视频详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(propagandaService.getById(id));
    }

    /**
     * 新增宣传视频
     */
    @Log(title = "宣传视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Propaganda propaganda) {
        return toAjax(propagandaService.save(propaganda) ? 1 : 0);
    }

    /**
     * 修改宣传视频
     */
    @Log(title = "宣传视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Propaganda propaganda) {
        if (Objects.equals("1", propaganda.getActived())) {
            Propaganda old = propagandaService.getOne(Wrappers.<Propaganda>lambdaQuery().eq(Propaganda::getActived, "1"));
            old.setActived("0");
            propagandaService.updateById(old);
        }
        boolean result = propagandaService.updateById(propaganda);
        return toAjax(result ? 1 : 0);
    }

    /**
     * 删除宣传视频
     */
    @Log(title = "宣传视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(propagandaService.removeByIds(Lists.newArrayList(ids)) ? 1 : 0);
    }
}
