package com.joolun.mall.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.entity.UserIncomeRecord;
import com.joolun.mall.enums.IncomeStatusEnum;
import com.joolun.mall.enums.ProductTypeEnum;
import com.joolun.mall.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class NotifyServiceImpl implements INotifyService {

    private final IUserMemberOrderService userMemberOrderService;

    private final IActivityOrderInfoService activityOrderInfoService;

    private final IUserPayRecordService userPayRecordService;

    private final IUserIncomeRecordService userIncomeRecordService;

    private final IUserCommissionService userCommissionService;


    /**
     * 微信回调通知业务处理
     *
     * @param notifyResult
     * @param productType
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notify(WxPayOrderNotifyResult notifyResult, ProductTypeEnum productType) {
        UserIncomeRecord userIncomeRecord = null;
        if (productType == ProductTypeEnum.MEMBER) {
            userIncomeRecord = userMemberOrderService.updateOrder(notifyResult);
        } else if (productType == ProductTypeEnum.ACTIVITY) {
            userIncomeRecord = activityOrderInfoService.notifyOrder(notifyResult);
        }
        userPayRecordService.addPayRecord(notifyResult, userIncomeRecord.getUserId(), productType);
        userIncomeRecordService.addUserIncomeRecord(userIncomeRecord);
        userCommissionService.updateCommissionIncomeData(userIncomeRecord, productType == ProductTypeEnum.MEMBER ?
                IncomeStatusEnum.COMPLETED : IncomeStatusEnum.IN_PROCESS);
    }
}
