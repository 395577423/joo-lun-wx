package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.common.utils.StringUtils;
import com.joolun.mall.entity.EmpowerVideo;
import com.joolun.mall.service.IEmpowerVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author Owen
 * @date 2022/4/16 21:23
 */
@RestController
@RequestMapping("/empower")
public class EmpowerVideoController extends BaseController {

    @Autowired
    private IEmpowerVideoService iEmpowerVideoService;

    /**
     * 查询赋能中心视频列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, EmpowerVideo video) {
        Page<EmpowerVideo> courses = iEmpowerVideoService.page(page, Wrappers.query(video));
        List<EmpowerVideo> records = courses.getRecords();
        courses.setRecords(records);
        return AjaxResult.success(courses);
    }

    /**
     * 获取赋能中心视频详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iEmpowerVideoService.getById(id));
    }

    /**
     * 新增赋能中心视频
     */
    @Log(title = "赋能中心视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmpowerVideo video) {
        if (null == video.getRates() || video.getRates().compareTo(BigDecimal.ZERO) == 0) {
            video.setRates(null);
        }
        boolean save = iEmpowerVideoService.save(video);
        //删除关联表中的记录
        return AjaxResult.success(save);
    }

    /**
     * 修改赋能中心视频
     */
    @Log(title = "赋能中心视频", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult edit(@RequestBody EmpowerVideo video) {
        if (null == video.getRates() || video.getRates().compareTo(BigDecimal.ZERO) == 0) {
            video.setRates(null);
        }
        boolean udpate = iEmpowerVideoService.updateById(video);
        return AjaxResult.success(udpate);
    }

    /**
     * 删除赋能中心视频
     */
    @Log(title = "赋能中心视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(iEmpowerVideoService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 查询赋能中心视频列表
     */
    @GetMapping("/list")
    public AjaxResult list(String title) {
        QueryWrapper<EmpowerVideo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }
        List<EmpowerVideo> list = iEmpowerVideoService.list(queryWrapper.lambda().select(EmpowerVideo::getId, EmpowerVideo::getTitle));
        return AjaxResult.success(list);
    }
}
