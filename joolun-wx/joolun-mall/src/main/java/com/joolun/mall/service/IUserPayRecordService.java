package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.entity.UserPayRecord;
import com.joolun.mall.enums.ProductTypeEnum;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lanjian
 * @date 2022-09-03
 */
public interface IUserPayRecordService extends IService<UserPayRecord> {

    void addPayRecord(WxPayOrderNotifyResult notifyResult, String userId, ProductTypeEnum productType);

}
