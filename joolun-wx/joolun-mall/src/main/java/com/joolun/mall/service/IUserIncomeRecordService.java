package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.dto.UserOrderBaseInfo;
import com.joolun.mall.entity.UserIncomeRecord;
import com.joolun.mall.enums.ProductTypeEnum;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lanjian
 * @date 2022-09-03
 */
public interface IUserIncomeRecordService extends IService<UserIncomeRecord> {


    /**
     * 增加用户收入记录
     *
     * @param orderBaseInfo
     */
    void addUserIncomeRecord(UserOrderBaseInfo orderBaseInfo);




}
