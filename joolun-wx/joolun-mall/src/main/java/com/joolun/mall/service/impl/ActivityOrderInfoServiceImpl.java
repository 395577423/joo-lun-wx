package com.joolun.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.constant.MallConstants;
import com.joolun.mall.dto.PriceCase;
import com.joolun.mall.entity.*;
import com.joolun.mall.enums.ActivityOrderInfoEnum;
import com.joolun.mall.enums.OrderInfoEnum;
import com.joolun.mall.mapper.ActivityOrderInfoMapper;
import com.joolun.mall.service.IActivityOrderInfoService;
import com.joolun.mall.service.IActivityService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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


    /**
     * 下单
     *
     * @param activityOrderInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(ActivityOrderInfo activityOrderInfo) {
        activityOrderInfo.setIsPay(CommonConstants.NO);
        activityOrderInfo.setPaymentWay("2");
        activityOrderInfo.setOrderNo(IdUtil.getSnowflake(0, 0).nextIdStr());
        activityOrderInfo.setStatus(ActivityOrderInfoEnum.STATUS_0.getValue());
        calPrice(activityOrderInfo);
        save(activityOrderInfo);
    }

    /**
     * 支付后更新订单状态
     *
     * @param orderInfo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notifyOrder(ActivityOrderInfo orderInfo) {
        if (CommonConstants.NO.equals(orderInfo.getIsPay())) {//只有未支付订单能操作
            orderInfo.setIsPay(CommonConstants.YES);
            orderInfo.setStatus(ActivityOrderInfoEnum.STATUS_1.getValue());
            updateById(orderInfo);//更新订单
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
        String subInfo = activity.getSubInfo();
        List<PriceCase> priceCases = JSON.parseArray(subInfo, PriceCase.class);
        if (CollectionUtil.isNotEmpty(priceCases)) {
            BigDecimal paymentPrice = priceCases.stream().map(PriceCase::getDiscountPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            activityOrderInfo.setPaymentPrice(paymentPrice);
            activityOrderInfo.setSalesPrice(paymentPrice);
        }
        activityOrderInfo.setPriceDesc(JSON.toJSONString(priceCases));

    }

    @Override
    public ActivityOrderInfo queryById(Long id) {
        ActivityOrderInfo activityOrderInfo = getById(id);
        if (activityOrderInfo != null) {
            String keyRedis = null;
            //获取自动取消倒计时
            if (CommonConstants.NO.equals(activityOrderInfo.getIsPay())) {
                keyRedis = String.valueOf(StrUtil.format("{}:{}",
                        MallConstants.REDIS_ACTIVITY_ORDER_KEY_IS_PAY_0, activityOrderInfo.getId()));
            }
            //获取自动收货倒计时
            if (OrderInfoEnum.STATUS_2.getValue().equals(activityOrderInfo.getStatus())) {
                keyRedis = String.valueOf(StrUtil.format("{}:{}",
                        MallConstants.REDIS_ACTIVITY_ORDER_KEY_STATUS_2, activityOrderInfo.getId()));
            }
        }
        return activityOrderInfo;
    }
}
