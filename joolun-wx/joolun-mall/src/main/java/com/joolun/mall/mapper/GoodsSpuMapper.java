
package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joolun.mall.entity.GoodsSpu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu商品
 *
 * @author Owen
 * @date 2019-08-12 16:25:10
 */
public interface GoodsSpuMapper extends BaseMapper<GoodsSpu> {

	IPage<GoodsSpu> selectPage1(IPage<GoodsSpu> page, @Param("query") GoodsSpu goodsSpu);

	GoodsSpu selectById1(String id);

	GoodsSpu selectById2(String id);

	GoodsSpu selectById4(String id);

	GoodsSpu selectOneByShoppingCart(String id);

	/**
	 * 查询电子券的关联商品
	 * @param couponId
	 * @return
	 */
	List<GoodsSpu> selectListByCouponId(String couponId);

}
