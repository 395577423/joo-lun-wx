package com.joolun.mall.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.ActivityOrderPerson;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lanjian
 * @date 2022-09-03
 */
public interface IActivityOrderPersonService extends IService<ActivityOrderPerson> {

    /**
     * 批量插入
     * @param activityOrderId
     * @param personId
     */
    void batchInsert(String activityOrderId,List<Long> personId);
}
