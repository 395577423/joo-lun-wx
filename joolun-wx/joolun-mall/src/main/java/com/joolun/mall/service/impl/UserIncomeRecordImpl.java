package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.dto.UserOrderBaseInfo;
import com.joolun.mall.entity.*;
import com.joolun.mall.enums.IncomeStatusEnum;
import com.joolun.mall.enums.MemberStatusEnum;
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

    /**
     * 增加用户收入记录
     *
     * @param orderBaseInfo
     */
    @Override
    public UserIncomeRecord addUserIncomeRecord(UserOrderBaseInfo orderBaseInfo) {
        UserIncomeRecord userIncomeRecord = null;
        if (ProductTypeEnum.MEMBER == orderBaseInfo.getProductType()) {
            userIncomeRecord = calMemberIncome(orderBaseInfo);
        } else if (ProductTypeEnum.ACTIVITY == orderBaseInfo.getProductType()) {
            userIncomeRecord = calActivityIncome(orderBaseInfo);
        }

        if (userIncomeRecord != null) {
            save(userIncomeRecord);
        }
        return userIncomeRecord;
    }


    /**
     * 计算会员分享的收入
     *
     * @param orderInfo
     */
    private UserIncomeRecord calMemberIncome(UserOrderBaseInfo orderInfo) {
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

            if (parentWxUser.isVip()) {
                UserMemberConfig userMemberConfig = userMemberConfigService.list().get(0);
                if (!sourceWxUser.isVip()) {
                    userIncomeRecord.setAmount(userMemberConfig.getCashBackAmount());
                }
                if (parentWxUser.isSVip()) {
                    if (!sourceWxUser.isVip()) {
                        userIncomeRecord.setAmount(userMemberConfig.getSuperCashBackAmount());
                    } else if (sourceWxUser.isVip()) {
                        userIncomeRecord.setAmount(userMemberConfig.getSuperCashBackAmount()
                                .subtract(userMemberConfig.getCashBackAmount()));
                    }
                }
            }
            return userIncomeRecord;
        }
        return null;
    }


    /**
     * 计算购买活动时的分享收入
     *
     * @param orderInfo
     */
    private UserIncomeRecord calActivityIncome(UserOrderBaseInfo orderInfo) {

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
            userIncomeRecord.setSourceType(ProductTypeEnum.ACTIVITY.getValue());
            userIncomeRecord.setCreateTime(new Date());
            userIncomeRecord.setOrderNo(orderInfo.getOrderNo());
            userIncomeRecord.setStatus(IncomeStatusEnum.IN_PROCESS.getValue());
            if (parentWxUser.isVip()) {
                Long priceCaseId = orderInfo.getPriceCaseId();
                ActivityPriceCase activityPriceCase = activityPriceCaseService.getById(priceCaseId);

                if (!sourceWxUser.isVip()) {
                    userIncomeRecord.setAmount(activityPriceCase.getCashBackAmount());
                }
                if (parentWxUser.isSVip()) {
                    if (!sourceWxUser.isVip()) {
                        userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount());
                    } else if (sourceWxUser.isVip()) {
                        userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount()
                                .subtract(activityPriceCase.getCashBackAmount()));
                    }
                }
            }
            return userIncomeRecord;
        }
        return null;
    }


}
