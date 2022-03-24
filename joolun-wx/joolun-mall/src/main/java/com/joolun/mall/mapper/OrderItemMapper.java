
package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.entity.OrderItem;

import java.util.List;

/**
 * 商城订单详情
 *
 * @author Owen
 * @date 2019-09-10 15:31:40
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

	List<OrderItem> selectList2(OrderItem orderItem);

}
