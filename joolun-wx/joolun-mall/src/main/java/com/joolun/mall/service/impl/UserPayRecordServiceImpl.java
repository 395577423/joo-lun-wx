package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.entity.UserMemberOrder;
import com.joolun.mall.entity.UserPayRecord;
import com.joolun.mall.enums.ProductTypeEnum;
import com.joolun.mall.mapper.UserPayRecordMapper;
import com.joolun.mall.service.IActivityOrderInfoService;
import com.joolun.mall.service.IUserMemberOrderService;
import com.joolun.mall.service.IUserPayRecordService;
import com.joolun.weixin.utils.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lanjian
 * @date 2022-09-03
 */
@Service
public class UserPayRecordServiceImpl extends ServiceImpl<UserPayRecordMapper, UserPayRecord> implements IUserPayRecordService {


    /**
     * 增加用户支付记录
     *
     * @param notifyResult
     * @param productType
     */
    @Override
    public void addPayRecord(WxPayOrderNotifyResult notifyResult, String userId, ProductTypeEnum productType) {
        String timeEnd = notifyResult.getTimeEnd();
        LocalDateTime paymentTime = LocalDateTimeUtils.parse(timeEnd);
        UserPayRecord userPayRecord = new UserPayRecord();
        userPayRecord.setUserId(userId);
        userPayRecord.setPaymentAmount(BigDecimal.valueOf(notifyResult.getTotalFee()).divide(BigDecimal.valueOf(100L)));
        userPayRecord.setPaymentTime(LocalDateTimeUtils.asDate(paymentTime));
        userPayRecord.setPaymentType(productType.getValue());
        userPayRecord.setTradeNo(notifyResult.getOutTradeNo());
        save(userPayRecord);
    }


}
