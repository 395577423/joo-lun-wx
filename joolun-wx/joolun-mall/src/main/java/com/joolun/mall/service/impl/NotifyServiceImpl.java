package com.joolun.mall.service.impl;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.dto.UserOrderBaseInfo;
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
        UserOrderBaseInfo userOrderBaseInfo = null;
        if (productType == ProductTypeEnum.MEMBER) {
            userOrderBaseInfo = userMemberOrderService.updateOrder(notifyResult);
        } else if (productType == ProductTypeEnum.ACTIVITY) {
            userOrderBaseInfo = activityOrderInfoService.notifyOrder(notifyResult);
        }
        if (userOrderBaseInfo != null) {
            userPayRecordService.addPayRecord(notifyResult, userOrderBaseInfo.getUserId(), productType);
            UserIncomeRecord userIncomeRecord = userIncomeRecordService.addUserIncomeRecord(userOrderBaseInfo);
            userCommissionService.updateCommissionIncomeData(userIncomeRecord, productType == ProductTypeEnum.MEMBER ?
                    IncomeStatusEnum.COMPLETED : IncomeStatusEnum.IN_PROCESS);
        }

    }
}
