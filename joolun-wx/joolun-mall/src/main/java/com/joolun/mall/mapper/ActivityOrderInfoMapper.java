
package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.entity.ActivityOrderInfo;
import com.joolun.mall.entity.OrderInfo;
import org.apache.ibatis.annotations.Select;

/**
 * 活动订单
 *
 * @author Owen
 * @date 2019-09-10 15:21:22
 */
public interface ActivityOrderInfoMapper extends BaseMapper<ActivityOrderInfo> {


    /**
     * 计算活动购买的人数
     * @param activityId
     * @return
     */
    @Select("SELECT COUNT(1) FROM `activity_order_info` t1 left join activity_order_person t2 on t1.id=t2.order_id " +
            "where t1.is_pay='1' and t1.activity_id=#{activityId}")
    int countPeoples(Long activityId);

}
