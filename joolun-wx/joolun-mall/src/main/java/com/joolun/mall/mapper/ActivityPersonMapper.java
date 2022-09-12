package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.entity.ActivityPerson;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lanjian
 * @Description
 * @create 2022-08-25
 */
public interface ActivityPersonMapper extends BaseMapper<ActivityPerson> {

    /**
     * 根据订单id查询出行人信息
     * @param activityOrderId
     * @return
     */
    @Select("SELECT t2.* FROM activity_order_person t1 join activity_person t2 on t1.person_id = t2.id WHERE " +
            " t1.order_id=#{activityOrderId}")
    List<ActivityPerson> queryByActivityOrderId(@Param("activityOrderId") String activityOrderId);


    /**
     * 根据活动id查找活动的人数
     * @param activityId
     * @return
     */
    @Select("select  t3.* from activity_order_info t1 left join activity_order_person t2 on t1.id=t2.order_id " +
            "left join activity_person t3 on t3.id=t2.person_id where t1.activity_id=#{activityId}")
    List<ActivityPerson> queryByActivityId(@Param("activityId") String activityId);
}
