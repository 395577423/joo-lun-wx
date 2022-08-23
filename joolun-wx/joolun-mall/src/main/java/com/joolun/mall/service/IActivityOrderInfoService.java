package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.ActivityOrderInfo;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-23
 */
public interface IActivityOrderInfoService extends IService<ActivityOrderInfo> {


    ActivityOrderInfo queryById(Long id);
}
