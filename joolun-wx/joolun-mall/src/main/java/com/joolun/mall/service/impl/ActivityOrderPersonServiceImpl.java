package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.ActivityOrderPerson;
import com.joolun.mall.mapper.ActivityOrderPersonMapper;
import com.joolun.mall.service.IActivityOrderPersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lanjian
 * @date 2022-09-03
 */
@Service
public class ActivityOrderPersonServiceImpl extends ServiceImpl<ActivityOrderPersonMapper, ActivityOrderPerson>
        implements IActivityOrderPersonService {


    /**
     * 批量插入
     *
     * @param activityOrderId
     * @param personIds
     */
    @Override
    public void batchInsert(String activityOrderId, List<Long> personIds) {
        List<ActivityOrderPerson> persons = new ArrayList<>();
        for (Long personId : personIds) {
            ActivityOrderPerson activityOrderPerson = new ActivityOrderPerson();
            activityOrderPerson.setOrderId(activityOrderId);
            activityOrderPerson.setPersonId(personId);
            persons.add(activityOrderPerson);
        }
        saveBatch(persons);
    }
}
