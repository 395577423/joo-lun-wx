package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.config.CommonConstants;
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

import java.math.BigDecimal;
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

    private static final int MEMBER_LEVEL_VALUE = 1;

    private static final int SUPER_LEVEL_VALUE = 2;

    /**
     * 增加用户收入记录
     *
     * @param orderBaseInfo
     */
    @Override
    public void addUserIncomeRecord(UserOrderBaseInfo orderBaseInfo) {
        if(ProductTypeEnum.MEMBER == orderBaseInfo.getProductType()){
            calMemberIncome(orderBaseInfo);
        } else if(ProductTypeEnum.ACTIVITY == orderBaseInfo.getProductType()){
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
        if(userShareRecord != null){
            String parentUserId = userShareRecord.getParentUserId();
            WxUser parentWxUser = wxUserService.getById(parentUserId);
            UserMemberConfig userMemberConfig = userMemberConfigService.list().get(0);
            if(parentWxUser.getLevel() == MEMBER_LEVEL_VALUE || parentWxUser.getLevel() == SUPER_LEVEL_VALUE){
                BigDecimal amount = parentWxUser.getLevel() == MEMBER_LEVEL_VALUE ?
                        userMemberConfig.getCashBackAmount() : userMemberConfig.getSuperCashBackAmount();
                UserIncomeRecord userIncomeRecord = createUserIncomeRecord(parentWxUser, sourceWxUser, ProductTypeEnum.MEMBER, orderInfo.getOrderNo(),
                        amount, IncomeStatusEnum.COMPLETED);
                save(userIncomeRecord);
                userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.COMPLETED);
            }

            //查找上上级
            if(parentWxUser.getLevel() != SUPER_LEVEL_VALUE){
                UserShareRecord parentUserShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                        .eq(UserShareRecord::getUserId, parentUserId));
                if(parentUserShareRecord != null){
                    String grantParentUserId = parentUserShareRecord.getParentUserId();
                    WxUser grantParentWxUser = wxUserService.getById(grantParentUserId);
                    if(grantParentWxUser.getLevel() == SUPER_LEVEL_VALUE){
                        BigDecimal amount = userMemberConfig.getSuperCashBackAmount().subtract(userMemberConfig.getCashBackAmount());
                        if(CommonConstants.NO.equals(parentWxUser.getMember())){
                            amount = userMemberConfig.getSuperCashBackAmount();
                        }
                        UserIncomeRecord topUserIncomeRecord = createUserIncomeRecord(grantParentWxUser, sourceWxUser,
                                ProductTypeEnum.MEMBER, orderInfo.getOrderNo(), amount, IncomeStatusEnum.COMPLETED);
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
        if(sourceWxUser.getLevel() == MEMBER_LEVEL_VALUE || sourceWxUser.getLevel() == SUPER_LEVEL_VALUE){
            BigDecimal amount = sourceWxUser.getLevel() == MEMBER_LEVEL_VALUE ?
                    activityPriceCase.getCashBackAmount() : activityPriceCase.getSuperCashBackAmount();
            UserIncomeRecord userIncomeRecord = createUserIncomeRecord(sourceWxUser, sourceWxUser, ProductTypeEnum.ACTIVITY,
                    orderInfo.getOrderNo(), amount, IncomeStatusEnum.IN_PROCESS);
            save(userIncomeRecord);
            userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.IN_PROCESS);
        }

        if(sourceWxUser.getLevel() == SUPER_LEVEL_VALUE){
            return;
        }

        UserShareRecord userShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getUserId, orderInfo.getUserId()));
        if(userShareRecord != null){
            String parentUserId = userShareRecord.getParentUserId();
            WxUser parentWxUser = wxUserService.getById(parentUserId);
            int levelDiff = parentWxUser.getLevel() - sourceWxUser.getLevel();
            if(levelDiff > 0){
                BigDecimal amount;
                if(parentWxUser.getLevel() == SUPER_LEVEL_VALUE && sourceWxUser.getLevel() == MEMBER_LEVEL_VALUE){
                    amount = activityPriceCase.getSuperCashBackAmount()
                            .subtract(activityPriceCase.getCashBackAmount());
                } else if(parentWxUser.getLevel() == MEMBER_LEVEL_VALUE && sourceWxUser.getLevel() == 0){
                    amount = activityPriceCase.getCashBackAmount();
                } else {
                    amount = activityPriceCase.getSuperCashBackAmount();
                }
                UserIncomeRecord userIncomeRecord = createUserIncomeRecord(parentWxUser, sourceWxUser, ProductTypeEnum.ACTIVITY,
                        orderInfo.getOrderNo(), amount, IncomeStatusEnum.IN_PROCESS);
                save(userIncomeRecord);
                userCommissionService.updateCommissionIncomeData(userIncomeRecord, IncomeStatusEnum.IN_PROCESS);
            }

            // 查找上上级别
            UserShareRecord parentUserShareRecord = userShareRecordService.getOne(Wrappers.<UserShareRecord>lambdaQuery()
                    .eq(UserShareRecord::getUserId, parentUserId).ne(UserShareRecord::getParentUserId, parentUserId));
            if(parentUserShareRecord != null){
                String grantParentUserId = parentUserShareRecord.getParentUserId();
                WxUser grantParentUser = wxUserService.getById(grantParentUserId);
                if(grantParentUser.getLevel() == SUPER_LEVEL_VALUE && parentWxUser.getLevel() != SUPER_LEVEL_VALUE
                        && sourceWxUser.getLevel() != SUPER_LEVEL_VALUE){
                    BigDecimal amount;
                    if(parentWxUser.getLevel() == 0 && sourceWxUser.getLevel() == 0){
                        amount = activityPriceCase.getSuperCashBackAmount();
                    } else {
                        amount = activityPriceCase.getSuperCashBackAmount().subtract(activityPriceCase.getCashBackAmount());
                    }
                    UserIncomeRecord parentUserIncomeRecord = createUserIncomeRecord(grantParentUser, sourceWxUser, ProductTypeEnum.ACTIVITY,
                            orderInfo.getOrderNo(), amount, IncomeStatusEnum.IN_PROCESS);
                    save(parentUserIncomeRecord);
                    userCommissionService.updateCommissionIncomeData(parentUserIncomeRecord, IncomeStatusEnum.IN_PROCESS);
                }
            }
        }


    }


    /**
     * 创建收入对象
     *
     * @param user
     * @param srcUser
     * @param type
     * @param orderNo
     * @param status
     * @return
     */
    private UserIncomeRecord createUserIncomeRecord(WxUser user, WxUser srcUser, ProductTypeEnum type, String orderNo,
                                                    BigDecimal amount, IncomeStatusEnum status) {
        UserIncomeRecord parentUserIncomeRecord = new UserIncomeRecord();
        parentUserIncomeRecord.setUserId(user.getId());
        parentUserIncomeRecord.setUserNickName(user.getNickName());
        parentUserIncomeRecord.setSourceUserId(srcUser.getId());
        parentUserIncomeRecord.setSourceUserNickName(srcUser.getNickName());
        parentUserIncomeRecord.setSourceType(type.getValue());
        parentUserIncomeRecord.setCreateTime(new Date());
        parentUserIncomeRecord.setOrderNo(orderNo);
        parentUserIncomeRecord.setAmount(amount);
        parentUserIncomeRecord.setStatus(status.getValue());
        return parentUserIncomeRecord;
    }

}
