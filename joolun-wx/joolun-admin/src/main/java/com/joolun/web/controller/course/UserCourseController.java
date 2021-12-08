package com.joolun.web.controller.course;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.annotation.Log;
import com.joolun.common.core.controller.BaseController;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.common.enums.BusinessType;
import com.joolun.mall.entity.UserCourse;
import com.joolun.mall.service.IUserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 用户购买课程Controller
 *
 * @author www.joolun.com
 * @date 2021-12-08
 */
@RestController
@RequestMapping("/course/usercourse")
public class UserCourseController extends BaseController {
    @Autowired
    private IUserCourseService userCourseService;

    /**
     * 查询用户购买课程列表
     */
    @GetMapping("/page")
    public AjaxResult list(Page page, UserCourse userCourse) {
        return AjaxResult.success(userCourseService.page(page, Wrappers.query(userCourse)));
    }

    /**
     * 获取用户购买课程详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userCourseService.getById(id));
    }

    /**
     * 新增用户购买课程
     */
    @Log(title = "用户购买课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserCourse userCourse) {
        return AjaxResult.success(userCourseService.save(userCourse));
    }

    /**
     * 修改用户购买课程
     */
    @Log(title = "用户购买课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserCourse userCourse) {
        return AjaxResult.success(userCourseService.updateById(userCourse));
    }

    /**
     * 删除用户购买课程
     */
    @Log(title = "用户购买课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return AjaxResult.success(userCourseService.removeByIds(Arrays.asList(ids)));
    }
}
