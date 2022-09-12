package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.ActivityPerson;
import com.joolun.mall.mapper.ActivityPersonMapper;
import com.joolun.mall.service.IActivityOrderPersonService;
import com.joolun.mall.service.IActivityPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-25
 */
@Service
public class ActivityPersonServiceImpl extends ServiceImpl<ActivityPersonMapper, ActivityPerson>
        implements IActivityPersonService {


    /**
     * 获取订单出行人信息
     *
     * @param activityId
     * @return
     */
    @Override
    public List<ActivityPerson> getActivityOrderPersons(String activityId) {
        return getBaseMapper().queryByActivityOrderId(activityId);
    }
}
