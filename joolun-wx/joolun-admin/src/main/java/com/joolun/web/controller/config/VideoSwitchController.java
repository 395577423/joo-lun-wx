package com.joolun.web.controller.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.VideoSwitch;
import com.joolun.mall.service.IVideoSwitchService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 视频开关
 *
 * @author Owen
 * @date 2022/10/24 19:56
 */
@RestController
@RequestMapping("/switch")
public class VideoSwitchController {

    @Resource
    private IVideoSwitchService iVideoSwitchService;

    /**
     * 查询课程列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, VideoSwitch videoSwitch) {
        Page<VideoSwitch> courses = iVideoSwitchService.page(page, Wrappers.query(videoSwitch));
        return AjaxResult.success(courses);
    }

    /**
     * 获取课程详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iVideoSwitchService.getById(id));
    }


    /**
     * 修改课程
     */
    @Log(title = "视频开关", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult edit(@RequestBody VideoSwitch videoSwitch) {
        boolean udpate = iVideoSwitchService.updateById(videoSwitch);
        return AjaxResult.success(udpate);
    }


}
