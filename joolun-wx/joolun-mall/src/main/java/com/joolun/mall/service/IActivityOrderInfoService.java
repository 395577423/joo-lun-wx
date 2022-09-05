package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.entity.UserIncomeRecord;

import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-23
 */
public interface IActivityOrderInfoService extends IService<ActivityOrderInfo> {


    void addOrder(ActivityOrderInfo activityOrderInfo);


    UserIncomeRecord notifyOrder(WxPayOrderNotifyResult notifyResult);


    ActivityOrderInfo queryById(Long id);


}
