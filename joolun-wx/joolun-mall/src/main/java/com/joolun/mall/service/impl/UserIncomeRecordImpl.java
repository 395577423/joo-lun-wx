package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.*;
import com.joolun.mall.enums.ProductTypeEnum;
import com.joolun.mall.mapper.UserIncomeRecordMapper;
import com.joolun.mall.service.*;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IActivityService activityService;

    @Autowired
    private ActivityPriceCaseService activityPriceCaseService;

    @Autowired
    private IActivityOrderInfoService activityOrderInfoService;

    @Autowired
    private IUserMemberOrderService userMemberOrderService;

    @Autowired
    private IUserShareRecordService userShareRecordService;

    @Autowired
    private WxUserService wxUserService;

    /**
     * 增加用户收入记录
     *
     * @param tradeNo
     * @param productType
     */
    @Override
    public void addUserIncomeRecord(String tradeNo, ProductTypeEnum productType) {
        switch (productType) {
            case GOODS:
            case COURSE:
            case MEMBER:
            case ACTIVITY:
        }

        UserIncomeRecord userIncomeRecord = new UserIncomeRecord();

    }

    /**
     * 计算会员分享的收入
     *
     * @param tradeNo
     */
    private UserIncomeRecord calMemberIncome(String tradeNo) {
        UserIncomeRecord userIncomeRecord = null;
        LambdaQueryWrapper<UserMemberOrder> queryWrapper = Wrappers.<UserMemberOrder>lambdaQuery().eq(UserMemberOrder::getOrderNo, tradeNo);
        UserMemberOrder userMemberOrder = userMemberOrderService.getOne(queryWrapper);
        String sourceUserId = userMemberOrder.getUserId();
        WxUser sourceWxUser = wxUserService.getById(sourceUserId);
        UserShareRecord userShareRecord = userShareRecordService.getOne(userShareRecordService.lambdaQuery()
                .eq(UserShareRecord::getUserId, sourceUserId));
        String parentUserId = userShareRecord.getParentUserId();
        WxUser parentWxUser = wxUserService.getById(parentUserId);
        if("1".equals(parentWxUser.getMember())){
            UserMemberConfig userMemberConfig = userMemberConfigService.list().get(0);
            userIncomeRecord = new UserIncomeRecord();
            userIncomeRecord.setUserId(parentUserId);
            userIncomeRecord.setUserNickName(parentWxUser.getNickName());
            userIncomeRecord.setSourceUserId(sourceUserId);
            userIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceType(ProductTypeEnum.MEMBER.getValue());
            if(parentWxUser.getLevel()==1){
                userIncomeRecord.setAmount(userMemberConfig.getCashBackAmount());
            }else if(parentWxUser.getLevel()==2){
                userIncomeRecord.setAmount(userMemberConfig.getSuperCashBackAmount());
            }
        }

        return userIncomeRecord;
    }

    /**
     * 计算购买活动时的分享收入
     *
     * @param tradeNo
     */
    private UserIncomeRecord calActivityIncome(String tradeNo) {
        UserIncomeRecord userIncomeRecord = null;
        ActivityOrderInfo activityOrderInfo = activityOrderInfoService.getOne(activityOrderInfoService.lambdaQuery()
                .eq(ActivityOrderInfo::getOrderNo, tradeNo));
        String sourceUserId = activityOrderInfo.getUserId();
        WxUser sourceWxUser = wxUserService.getById(sourceUserId);
        UserShareRecord userShareRecord = userShareRecordService.getOne(userShareRecordService.lambdaQuery()
                .eq(UserShareRecord::getUserId, sourceUserId));
        String parentUserId = userShareRecord.getParentUserId();
        WxUser parentWxUser = wxUserService.getById(parentUserId);
        if("1".equals(parentWxUser.getMember())){
            Long priceCaseId = activityOrderInfo.getPriceCaseId();
            ActivityPriceCase activityPriceCase = activityPriceCaseService.getById(priceCaseId);
            userIncomeRecord = new UserIncomeRecord();
            userIncomeRecord.setUserId(parentUserId);
            userIncomeRecord.setUserNickName(parentWxUser.getNickName());
            userIncomeRecord.setSourceUserId(sourceUserId);
            userIncomeRecord.setSourceUserNickName(sourceWxUser.getNickName());
            userIncomeRecord.setSourceType(ProductTypeEnum.ACTIVITY.getValue());
            if(parentWxUser.getLevel()==1){
                userIncomeRecord.setAmount(activityPriceCase.getCashBackAmount());
            }else if(parentWxUser.getLevel()==2){
                userIncomeRecord.setAmount(activityPriceCase.getSuperCashBackAmount());
            }
        }
        return userIncomeRecord;

    }

}
