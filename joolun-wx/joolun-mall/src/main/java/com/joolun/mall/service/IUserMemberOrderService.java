package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.entity.UserIncomeRecord;
import com.joolun.mall.entity.UserMemberOrder;

import java.math.BigDecimal;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lanjian
 * @date 2022-09-03
 */
public interface IUserMemberOrderService extends IService<UserMemberOrder> {


    /**
     * 创建会员订单
     *
     * @param price
     * @param userId
     * @return
     */
    UserMemberOrder createOrder(BigDecimal price, String userId);

    /**
     * 更新订单
     *
     * @param notifyResult
     */
    UserIncomeRecord updateOrder(WxPayOrderNotifyResult notifyResult);
}
