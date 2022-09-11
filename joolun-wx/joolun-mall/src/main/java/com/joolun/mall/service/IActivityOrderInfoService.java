package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.common.core.domain.AjaxResult;
import com.joolun.mall.dto.UserOrderBaseInfo;
import com.joolun.mall.entity.ActivityOrderInfo;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-23
 */
public interface IActivityOrderInfoService extends IService<ActivityOrderInfo> {


    void addOrder(ActivityOrderInfo activityOrderInfo);


    UserOrderBaseInfo notifyOrder(WxPayOrderNotifyResult notifyResult);


    ActivityOrderInfo queryById(Long id);


    /**
     * 计算已购买活动的人数
     * @param activityId
     * @return
     */
    AjaxResult getActivityClosed(Long activityId);
}
