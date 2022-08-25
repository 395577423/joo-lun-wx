package com.joolun.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.config.CommonConstants;
import com.joolun.mall.constant.MallConstants;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.enums.OrderInfoEnum;
import com.joolun.mall.mapper.ActivityOrderInfoMapper;
import com.joolun.mall.service.IActivityOrderInfoService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
