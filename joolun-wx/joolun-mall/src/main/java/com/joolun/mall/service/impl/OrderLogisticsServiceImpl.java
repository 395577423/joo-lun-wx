
package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.OrderLogistics;
import com.joolun.mall.mapper.OrderLogisticsMapper;
import com.joolun.mall.service.OrderLogisticsService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 订单物流
 *
 * @author Owen
 * @date 2019-09-16 09:53:17
 */
@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements OrderLogisticsService {

	@Override
	public OrderLogistics getById(Serializable id) {
		return baseMapper.selectById2(id);
	}
}
