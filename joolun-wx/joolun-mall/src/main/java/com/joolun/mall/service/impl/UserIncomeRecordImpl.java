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
                if ("1".equals(parentWxUser.getPartner()) && "2".equals(parentWxUser.getVipType())) {
                    userIncomeRecord.setAmount(userMemberConfig.getSuperCashBackAmount());
                }
            }
            save(userIncomeRecord);
            userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.COMPLETED);

            //查找上上级
            if (!"1".equals(parentWxUser.getPartner())) {
                UserShareRecord parentUserShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                        .eq(UserShareRecord::getUserId, parentUserId));
                if (parentUserShareRecord != null) {
                    String grantParentUserId = parentUserShareRecord.getParentUserId();
                    WxUser grantParentWxUser = wxUserService.getById(grantParentUserId);
                    if ("1".equals(grantParentWxUser.getPartner()) && "2".equals(parentWxUser.getVipType())) {
                        UserMemberConfig userMemberConfig = userMemberConfigService.list().get(0);
                        UserIncomeRecord topUserIncomeRecord = new UserIncomeRecord();
                        topUserIncomeRecord.setUserId(grantParentUserId);
                        topUserIncomeRecord.setUserNickName(grantParentWxUser.getNickName());
                        topUserIncomeRecord.setSourceUserId(orderInfo.getUserId());
                        topUserIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
                        topUserIncomeRecord.setSourceType(ProductTypeEnum.MEMBER.getValue());
                        topUserIncomeRecord.setCreateTime(new Date());
                        topUserIncomeRecord.setOrderNo(orderInfo.getOrderNo());
                        topUserIncomeRecord.setStatus(IncomeStatusEnum.COMPLETED.getValue());
                        topUserIncomeRecord.setAmount(userMemberConfig.getSuperCashBackAmount()
                                .subtract(userMemberConfig.getCashBackAmount()));
                        save(topUserIncomeRecord);
                        userCommissionService.updateCommissionIncomeData(topUserIncomeRecord, IncomeStatusEnum.COMPLETED);
                    }
                }
            }


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

        if ("1".equals(sourceWxUser.getPartner())) {
            return;
        }

        UserShareRecord userShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getUserId, orderInfo.getUserId()));
        if (userShareRecord != null) {
            String parentUserId = userShareRecord.getParentUserId();
            WxUser parentWxUser = wxUserService.getById(parentUserId);
            UserIncomeRecord userIncomeRecord = new UserIncomeRecord();
            userIncomeRecord.setUserId(parentWxUser.getId());
            userIncomeRecord.setUserNickName(parentWxUser.getNickName());
            userIncomeRecord.setSourceUserId(orderInfo.getUserId());
            userIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceType(ProductTypeEnum.ACTIVITY.getValue());
            userIncomeRecord.setCreateTime(new Date());
            userIncomeRecord.setOrderNo(orderInfo.getOrderNo());
            userIncomeRecord.setStatus(IncomeStatusEnum.IN_PROCESS.getValue());
            if ("1".equals(parentWxUser.getPartner()) && "2".equals(parentWxUser.getVipType())) {
                if (sourceWxUser.getVip()) {
                    userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount()
                            .subtract(activityPriceCase.getCashBackAmount()));
                } else {
                    userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount());
                }
            } else if (parentWxUser.getVip()) {
                if (sourceWxUser.getVip()) {
                    userIncomeRecord = null;
                } else {
                    userIncomeRecord.setAmount(activityPriceCase.getCashBackAmount());
                }
            }
            if (userIncomeRecord != null) {
                save(userIncomeRecord);
                userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.IN_PROCESS);
            }

            // 查找上上级别
            UserShareRecord parentUserShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                    .eq(UserShareRecord::getUserId, parentUserId).ne(UserShareRecord::getParentUserId, parentUserId));
            if (parentUserShareRecord != null) {
                String grantParentUserId = parentUserShareRecord.getParentUserId();
                WxUser grantParentUser = wxUserService.getById(grantParentUserId);
                UserIncomeRecord parentUserIncomeRecord = new UserIncomeRecord();
                parentUserIncomeRecord.setUserId(grantParentUser.getId());
                parentUserIncomeRecord.setUserNickName(grantParentUser.getNickName());
                parentUserIncomeRecord.setSourceUserId(orderInfo.getUserId());
                parentUserIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
                parentUserIncomeRecord.setSourceType(ProductTypeEnum.ACTIVITY.getValue());
                parentUserIncomeRecord.setCreateTime(new Date());
                parentUserIncomeRecord.setOrderNo(orderInfo.getOrderNo());
                parentUserIncomeRecord.setStatus(IncomeStatusEnum.IN_PROCESS.getValue());
                if ("1".equals(grantParentUser.getPartner()) && "2".equals(parentWxUser.getVipType())) {
                    if (parentWxUser.getVip() && !"1".equals(sourceWxUser.getPartner())) {
                        parentUserIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount()
                                .subtract(activityPriceCase.getCashBackAmount()));
                    } else {
                        parentUserIncomeRecord = null;
                    }
                }
                if (parentUserIncomeRecord != null) {
                    save(parentUserIncomeRecord);
                    userCommissionService.updateCommissionIncomeData(parentUserIncomeRecord, IncomeStatusEnum.IN_PROCESS);
                }
            }

        }


    }


}
