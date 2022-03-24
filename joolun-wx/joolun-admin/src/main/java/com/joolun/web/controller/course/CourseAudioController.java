package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.CourseAudio;
import com.joolun.mall.service.ICourseAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 课程音频
 * @author Owen
 * @date 2022/3/21 15:31
 */
@RestController
@RequestMapping("/course/audio")
public class CourseAudioController extends BaseController {

    @Autowired
    private ICourseAudioService iCourseAudioService;

    /**
     * 查询书籍音频列表
     */
    @GetMapping("/page")
    public AjaxResult page(Page page, CourseAudio courseAudio) {
        return AjaxResult.success(iCourseAudioService.page(page, Wrappers.query(courseAudio)));
    }

    /**
     * 获取书籍音频详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(iCourseAudioService.getById(id));
    }

    /**
     * 新增书籍音频
     */
    @Log(title = "书籍音频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseAudio courseAudio) {
        return AjaxResult.success(iCourseAudioService.save(courseAudio));
    }

    /**
     * 修改书籍音频
     */
    @Log(title = "书籍音频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseAudio courseAudio) {
        return AjaxResult.success(iCourseAudioService.updateById(courseAudio));
    }

    /**
     * 删除书籍音频
     */
    @Log(title = "书籍音频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(iCourseAudioService.removeByIds(Arrays.asList(ids)));
    }
}
