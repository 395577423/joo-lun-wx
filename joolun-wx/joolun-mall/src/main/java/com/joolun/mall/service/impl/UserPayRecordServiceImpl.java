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

    @Autowired
    private IActivityOrderInfoService activityOrderInfoService;

    @Autowired
    private IUserMemberOrderService userMemberOrderService;

    /**
     * 增加用户支付记录
     * @param notifyResult
     * @param productType
     */
    @Override
    public void addPayRecord(WxPayOrderNotifyResult notifyResult, ProductTypeEnum productType) {
        String timeEnd = notifyResult.getTimeEnd();
        LocalDateTime paymentTime = LocalDateTimeUtils.parse(timeEnd);
        UserPayRecord userPayRecord = new UserPayRecord();
        userPayRecord.setUserId(getUserId(notifyResult.getOutTradeNo(),productType));
        userPayRecord.setPaymentAmount(BigDecimal.valueOf(notifyResult.getTotalFee()).divide(BigDecimal.valueOf(100L)));
        userPayRecord.setPaymentTime(LocalDateTimeUtils.asDate(paymentTime));
        userPayRecord.setPaymentType(productType.getValue());
        save(userPayRecord);
    }

    /**
     * 获取用户id
     * @param tradeNo
     * @param productType
     * @return
     */
   private String getUserId(String tradeNo,ProductTypeEnum productType){

       String userId;
       if(ProductTypeEnum.ACTIVITY == productType) {
           ActivityOrderInfo activityOrderInfo = activityOrderInfoService.getOne(Wrappers.<ActivityOrderInfo>lambdaQuery()
                   .eq(ActivityOrderInfo::getOrderNo, tradeNo));
           userId = activityOrderInfo.getUserId();
       }else if(ProductTypeEnum.MEMBER == productType) {
           LambdaQueryWrapper<UserMemberOrder> queryWrapper = Wrappers.<UserMemberOrder>lambdaQuery().eq(UserMemberOrder::getOrderNo, tradeNo);
           UserMemberOrder userMemberOrder = userMemberOrderService.getOne(queryWrapper);
           userId = userMemberOrder.getUserId();
       }else{
           userId = null;
       }

       return userId;
    }

}
