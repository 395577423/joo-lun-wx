package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.dto.UserOrderBaseInfo;
import com.joolun.mall.entity.ActivityPriceCase;
import com.joolun.mall.entity.UserIncomeRecord;
import com.joolun.mall.entity.UserMemberConfig;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.mall.enums.IncomeStatusEnum;
import com.joolun.mall.enums.ProductTypeEnum;
import com.joolun.mall.mapper.UserIncomeRecordMapper;
import com.joolun.mall.service.*;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lanjian
 * @Description
 * @create 2022-09-03
 */
@Service
public class UserIncomeRecordImpl extends ServiceImpl<UserIncomeRecordMapper, UserIncomeRecord>
        implements IUserIncomeRecordService {


    @Autowired
    private IUserMemberConfigService userMemberConfigService;

    @Autowired
    WxUserService wxUserService;

    @Autowired
    private IUserShareRecordService userShareRecordService;

    @Autowired
    private ActivityPriceCaseService activityPriceCaseService;

    @Autowired
    private IUserCommissionService userCommissionService;

    /**
     * 增加用户收入记录
     *
     * @param orderBaseInfo
     */
    @Override
    public void addUserIncomeRecord(UserOrderBaseInfo orderBaseInfo) {
        UserIncomeRecord userIncomeRecord = null;
        if (ProductTypeEnum.MEMBER == orderBaseInfo.getProductType()) {
            calMemberIncome(orderBaseInfo);
        } else if (ProductTypeEnum.ACTIVITY == orderBaseInfo.getProductType()) {
            calActivityIncome(orderBaseInfo);
        }

    }


    /**
     * 计算会员分享的收入
     *
     * @param orderInfo
     */
    private void calMemberIncome(UserOrderBaseInfo orderInfo) {
        WxUser sourceWxUser = wxUserService.getById(orderInfo.getUserId());
        UserShareRecord userShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getUserId, orderInfo.getUserId()));
        if (userShareRecord != null) {
            String parentUserId = userShareRecord.getParentUserId();
            WxUser parentWxUser = wxUserService.getById(parentUserId);
            UserIncomeRecord userIncomeRecord = new UserIncomeRecord();
            userIncomeRecord.setUserId(parentUserId);
            userIncomeRecord.setUserNickName(parentWxUser.getNickName());
            userIncomeRecord.setSourceUserId(orderInfo.getUserId());
            userIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceType(ProductTypeEnum.MEMBER.getValue());
            userIncomeRecord.setCreateTime(new Date());
            userIncomeRecord.setOrderNo(orderInfo.getOrderNo());
            userIncomeRecord.setStatus(IncomeStatusEnum.COMPLETED.getValue());
            if (parentWxUser.getVip()) {
                UserMemberConfig userMemberConfig = userMemberConfigService.list().get(0);
                if (!sourceWxUser.getVip()) {
                    userIncomeRecord.setAmount(userMemberConfig.getCashBackAmount());
                }
                if ("1".equals(parentWxUser.getPartner())) {
                    if (!sourceWxUser.getVip()) {
                        userIncomeRecord.setAmount(userMemberConfig.getSuperCashBackAmount());
                    } else if (sourceWxUser.getVip()) {
                        userIncomeRecord.setAmount(userMemberConfig.getSuperCashBackAmount()
                                .subtract(userMemberConfig.getCashBackAmount()));
                    }
                }
            }
            save(userIncomeRecord);
            userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.COMPLETED);
        }

    }


    /**
     * 计算购买活动时的分享收入
     *
     * @param orderInfo
     */
    private void calActivityIncome(UserOrderBaseInfo orderInfo) {

        WxUser sourceWxUser = wxUserService.getById(orderInfo.getUserId());
        Long priceCaseId = orderInfo.getPriceCaseId();
        ActivityPriceCase activityPriceCase = activityPriceCaseService.getById(priceCaseId);
        if (sourceWxUser.getVip()) {
            UserIncomeRecord userIncomeRecord = new UserIncomeRecord();
            userIncomeRecord.setUserId(sourceWxUser.getId());
            userIncomeRecord.setUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceUserId(orderInfo.getUserId());
            userIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceType(ProductTypeEnum.ACTIVITY.getValue());
            userIncomeRecord.setCreateTime(new Date());
            userIncomeRecord.setOrderNo(orderInfo.getOrderNo());
            userIncomeRecord.setStatus(IncomeStatusEnum.IN_PROCESS.getValue());
            userIncomeRecord.setAmount(activityPriceCase.getCashBackAmount());
            if ("1".equals(sourceWxUser.getPartner())) {
                userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount());
            }
            save(userIncomeRecord);
            userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.IN_PROCESS);
        }

        UserShareRecord userShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getUserId, orderInfo.getUserId()));
        if (userShareRecord != null) {
            String parentUserId = userShareRecord.getParentUserId();
            WxUser parentWxUser = wxUserService.getById(parentUserId);
            UserIncomeRecord userIncomeRecord = new UserIncomeRecord();
            userIncomeRecord.setUserId(parentWxUser.getId());
            userIncomeRecord.setUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceUserId(orderInfo.getUserId());
            userIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceType(ProductTypeEnum.ACTIVITY.getValue());
            userIncomeRecord.setCreateTime(new Date());
            userIncomeRecord.setOrderNo(orderInfo.getOrderNo());
            userIncomeRecord.setStatus(IncomeStatusEnum.IN_PROCESS.getValue());
            if ("1".equals(parentWxUser.getPartner())) {
                if ("1".equals(parentWxUser.getPartner())) {
                    userIncomeRecord = null;
                }
                if (sourceWxUser.getVip()) {
                    userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount()
                            .subtract(activityPriceCase.getCashBackAmount()));
                } else {
                    userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount());
                }
            } else if (parentWxUser.getVip()) {
                if ("1".equals(parentWxUser.getPartner()) || sourceWxUser.getVip()) {
                    userIncomeRecord = null;
                } else {
                    userIncomeRecord.setAmount(activityPriceCase.getCashBackAmount());
                }
            }
            if(userIncomeRecord != null ){
                save(userIncomeRecord);
                userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.IN_PROCESS);
            }
        }

    }


}
