package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.ActivityPerson;
import com.joolun.mall.mapper.ActivityPersonMapper;
import com.joolun.mall.service.IActivityPersonService;
import org.springframework.stereotype.Service;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-25
 */
@Service
public class ActivityPersonServiceImpl extends ServiceImpl<ActivityPersonMapper, ActivityPerson>
        implements IActivityPersonService {

}
