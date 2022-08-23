package com.joolun.web.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.constant.MallConstants;
import com.joolun.mall.dto.PlaceOrderDTO;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.entity.OrderInfo;
import com.joolun.mall.service.IActivityOrderInfoService;
import com.joolun.weixin.constant.MyReturnCode;
import com.joolun.weixin.utils.ThirdSessionHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/api/ma/activity/order/info")
@Api(value = "activityOrderInfo", tags = "商城订单API")
public class ActivityOrderApi {

    @Autowired
    private IActivityOrderInfoService activityOrderInfoService;

    /**
     * 分页查询
     *
     * @param page      分页对象
     * @param orderInfo 商城订单
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public AjaxResult getOrderInfoPage(Page page, ActivityOrderInfo orderInfo) {
        orderInfo.setUserId(ThirdSessionHolder.getWxUserId());
        LambdaQueryWrapper<ActivityOrderInfo> queryWrapper = Wrappers.lambdaQuery(orderInfo);
        return AjaxResult.success(activityOrderInfoService.page(page, queryWrapper));
    }


    /**
     * 通过id查询活动订单
     *
     * @param id
     * @return R
     */
    @ApiOperation(value = "通过id查询商城订单")
    @GetMapping("/{id}")
    public AjaxResult getById(HttpServletRequest request, @PathVariable("id") Long id) {
        return AjaxResult.success(activityOrderInfoService.queryById(id));
    }


    /**
     * 新增商城订单
     *
     * @param placeOrderDTO 商城订单
     * @return R
     */
    @ApiOperation(value = "新增活动订单")
    @PostMapping
    public AjaxResult save(@RequestBody PlaceOrderDTO placeOrderDTO) {

        return AjaxResult.success();
    }
}
