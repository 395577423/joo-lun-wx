package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.UserAnswer;
import com.joolun.mall.service.IUserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 用户答案Controller
 *
 * @author Owen
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course/answer")
public class UserAnswerController extends BaseController {
    @Autowired
    private IUserAnswerService userAnswerService;

    /**
     * 查询用户答案列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, UserAnswer userAnswer) {
        return AjaxResult.success(userAnswerService.page(page, Wrappers.query(userAnswer)));
    }

    /**
     * 获取用户答案详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userAnswerService.getById(id));
    }

    /**
     * 新增用户答案
     */
    @Log(title = "用户答案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserAnswer userAnswer) {
        return AjaxResult.success(userAnswerService.save(userAnswer));
    }

    /**
     * 修改用户答案
     */
    @Log(title = "用户答案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAnswer userAnswer) {
        return AjaxResult.success(userAnswerService.updateById(userAnswer));
    }

    /**
     * 删除用户答案
     */
    @Log(title = "用户答案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(userAnswerService.removeByIds(Arrays.asList(ids)));
    }
}
