package com.joolun.mall.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.enums.ProductTypeEnum;

public interface INotifyService {


    /**
     * 微信回调通知业务处理
     * @param notifyResult
     * @param productType
     */
    void notify(WxPayOrderNotifyResult notifyResult, ProductTypeEnum productType);

}
