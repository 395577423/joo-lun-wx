package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.ActivityPerson;

import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-23
 */
public interface IActivityPersonService extends IService<ActivityPerson> {

    /**
     * 获取订单出行人信息
     * @param activityId
     * @return
     */
    List<ActivityPerson> getActivityOrderPersons(String activityId);

}
