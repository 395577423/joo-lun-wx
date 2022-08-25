package com.joolun.web.api;

import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.service.IActivityOrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 订单
 *
 * @author YangLinWei
 * @date 2022-08-25 14:06:58
 */
@Api(tags = "订单")
@Slf4j
@RestController
@RequestMapping("/activity/orderinfo")
public class ActivityOrderInfoApi {
    @Autowired
    private IActivityOrderInfoService activityOrderInfoService;


    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @GetMapping("/{id}")
    public AjaxResult findById(@PathVariable Long id) {
        ActivityOrderInfo model = activityOrderInfoService.getById(id);
        return AjaxResult.success(model);
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping
    public AjaxResult save(@RequestBody ActivityOrderInfo activityOrderInfo) {
            activityOrderInfoService.saveOrUpdate(activityOrderInfo);
        return AjaxResult.success("保存成功");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id) {
            activityOrderInfoService.removeById(id);
        return AjaxResult.success("删除成功");
    }
}
