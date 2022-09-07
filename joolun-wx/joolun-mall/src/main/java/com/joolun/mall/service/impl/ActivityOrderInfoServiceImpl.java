package com.joolun.mall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.constant.MallConstants;
import com.joolun.mall.dto.UserOrderBaseInfo;
import com.joolun.mall.entity.*;
import com.joolun.mall.enums.*;
import com.joolun.mall.mapper.ActivityOrderInfoMapper;
import com.joolun.mall.service.*;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import com.joolun.weixin.utils.LocalDateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-23
 */
@AllArgsConstructor
@Service
public class ActivityOrderInfoServiceImpl extends ServiceImpl<ActivityOrderInfoMapper, ActivityOrderInfo>
        implements IActivityOrderInfoService {

    private final RedisTemplate<String, String> redisTemplate;

    private final IActivityService activityService;

    private final IActivityOrderPersonService activityOrderPersonService;

    private final IActivityPersonService activityPersonService;

    private final WxUserService wxUserService;

    @Autowired
    private ActivityPriceCaseService activityPriceCaseService;

    /**
     * 下单
     *
     * @param activityOrderInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(ActivityOrderInfo activityOrderInfo) {
        List<Long> personIds = activityOrderInfo.getPersonIds();
        activityOrderInfo.setIsPay(CommonConstants.NO);
        activityOrderInfo.setPaymentWay("2");
        activityOrderInfo.setOrderNo(IdUtil.getSnowflake(0, 0).nextIdStr());
        activityOrderInfo.setStatus(ActivityOrderInfoEnum.STATUS_0.getValue());
        calPrice(activityOrderInfo);
        save(activityOrderInfo);
        activityOrderPersonService.batchInsert(activityOrderInfo.getId(), personIds);
    }

    /**
     * 支付后更新订单状态
     *
     * @param notifyResult
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserOrderBaseInfo notifyOrder(WxPayOrderNotifyResult notifyResult) {
        ActivityOrderInfo orderInfo = getOne(Wrappers.<ActivityOrderInfo>lambdaQuery()
                .eq(ActivityOrderInfo::getOrderNo, notifyResult.getOutTradeNo()));
        if (orderInfo != null) {
            if (orderInfo.getPaymentPrice().multiply(new BigDecimal(100)).intValue() == notifyResult.getTotalFee()) {
                String timeEnd = notifyResult.getTimeEnd();
                LocalDateTime paymentTime = LocalDateTimeUtils.parse(timeEnd);
                orderInfo.setPaymentTime(LocalDateTimeUtils.asDate(paymentTime));
                orderInfo.setTransactionId(notifyResult.getTransactionId());
                if (CommonConstants.NO.equals(orderInfo.getIsPay())) {//只有未支付订单能操作
                    orderInfo.setIsPay(CommonConstants.YES);
                    orderInfo.setStatus(ActivityOrderInfoEnum.STATUS_1.getValue());
                    updateById(orderInfo);//更新订单
                    UserOrderBaseInfo userOrderBaseInfo = BeanUtil.toBean(orderInfo, UserOrderBaseInfo.class);
                    return userOrderBaseInfo;
                } else {
                    throw new RuntimeException("订单已支付");
                }
            } else {
                throw new RuntimeException("付款金额与订单金额不等");
            }
        } else {
            throw new RuntimeException("无此订单");
        }
    }

    /**
     * 计算价格
     *
     * @param activityOrderInfo
     */
    private void calPrice(ActivityOrderInfo activityOrderInfo) {
        Activity activity = activityService.getById(activityOrderInfo.getActivityId());
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }
        String userId = activityOrderInfo.getUserId();
        WxUser wxUser = wxUserService.getById(userId);

        Long priceCaseId = activityOrderInfo.getPriceCaseId();
        ActivityPriceCase activityPriceCase = activityPriceCaseService.getById(priceCaseId);
        if (wxUser.isSVip()) {
            activityOrderInfo.setPaymentPrice(activityPriceCase.getSuperMemberPrice());
        } else if (wxUser.isVip()) {
            activityOrderInfo.setPaymentPrice(activityPriceCase.getMemberPrice());
        } else {
            activityOrderInfo.setPaymentPrice(activityPriceCase.getSalesPrice());
        }
        activityOrderInfo.setSalesPrice(activityPriceCase.getSalesPrice());
    }

    @Override
    public ActivityOrderInfo queryById(Long id) {
        ActivityOrderInfo activityOrderInfo = getById(id);
        if (activityOrderInfo != null) {
            String keyRedis = null;
            //获取自动取消倒计时
            if (CommonConstants.NO.equals(activityOrderInfo.getIsPay())) {
                keyRedis = String.valueOf(StrUtil.format("{}:{}" ,
                        MallConstants.REDIS_ACTIVITY_ORDER_KEY_IS_PAY_0, activityOrderInfo.getId()));
            }
            //获取自动收货倒计时
            if (OrderInfoEnum.STATUS_2.getValue().equals(activityOrderInfo.getStatus())) {
                keyRedis = String.valueOf(StrUtil.format("{}:{}" ,
                        MallConstants.REDIS_ACTIVITY_ORDER_KEY_STATUS_2, activityOrderInfo.getId()));
            }
        }
        LambdaQueryWrapper<ActivityOrderPerson> queryWrapper = Wrappers.<ActivityOrderPerson>lambdaQuery()
                .eq(ActivityOrderPerson::getOrderId, id);
        List<ActivityOrderPerson> orderPersons = activityOrderPersonService.list(queryWrapper);
        List<Long> personIds = orderPersons.stream().map(ActivityOrderPerson::getPersonId).collect(Collectors.toList());
        List<ActivityPerson> activityPeoples = activityPersonService.listByIds(personIds);
        activityOrderInfo.setPersons(activityPeoples);
        return activityOrderInfo;
    }

}
