package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.UserAudio;
import com.joolun.mall.service.IUserAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 用户书籍录音Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course/audio")
public class UserAudioController extends BaseController {
    @Autowired
    private IUserAudioService userAudioService;

    /**
     * 查询用户书籍录音列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, UserAudio userAudio) {
        return AjaxResult.success(userAudioService.page(page, Wrappers.query(userAudio)));
    }


    /**
     * 获取用户书籍录音详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userAudioService.getById(id));
    }

    /**
     * 新增用户书籍录音
     */
    @Log(title = "用户书籍录音", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserAudio userAudio) {
        return AjaxResult.success(userAudioService.save(userAudio));
    }

    /**
     * 修改用户书籍录音
     */
    @Log(title = "用户书籍录音", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAudio userAudio) {
        return AjaxResult.success(userAudioService.updateById(userAudio));
    }

    /**
     * 删除用户书籍录音
     */
    @Log(title = "用户书籍录音", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(userAudioService.removeByIds(Arrays.asList(ids)));
    }
}
