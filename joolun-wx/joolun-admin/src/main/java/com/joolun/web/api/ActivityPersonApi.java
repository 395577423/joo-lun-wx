package com.joolun.web.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.entity.ActivityPerson;
import com.joolun.mall.service.IActivityPersonService;
import com.joolun.weixin.utils.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author YangLinWei
 * @date 2022-08-25 14:06:58
 */
@Slf4j
@RestController
@RequestMapping("/weixin/api/activity/person")
public class ActivityPersonApi {
    @Autowired
    private IActivityPersonService activityPersonService;


    /**
     * 查看当前登录人所有的人员信息
     *
     * @return
     */
    @ApiOperation(value = "获取当前用户的出行人信息")
    @GetMapping("/list")
    public AjaxResult list() {
        String wxUserId = ThirdSessionHolder.getWxUserId();
        LambdaQueryWrapper<ActivityPerson> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ActivityPerson::getUserId, wxUserId);
        List<ActivityPerson> persons = activityPersonService.list(queryWrapper);
        return AjaxResult.success(persons);
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @GetMapping("/{id}")
    public AjaxResult findById(@PathVariable Long id) {
        ActivityPerson model = activityPersonService.getById(id);
        return AjaxResult.success("查询成功", model);
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody ActivityPerson activityPerson) {
        String wxUserId = ThirdSessionHolder.getWxUserId();
        activityPerson.setUserId(wxUserId);
        if(activityPerson.getId() == null){
            activityPerson.setCreateBy(wxUserId);
            activityPerson.setCreateTime(new Date());
        }else {
            activityPerson.setUpdateBy(wxUserId);
            activityPerson.setUpdateTime(new Date());
        }
        activityPersonService.saveOrUpdate(activityPerson);
        return AjaxResult.success("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        activityPersonService.removeById(id);
        return AjaxResult.success("删除成功");
    }
}
