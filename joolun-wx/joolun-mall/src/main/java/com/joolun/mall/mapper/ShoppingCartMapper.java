
package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.joolun.mall.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;

/**
 * 购物车
 *
 * @author Owen
 * @date 2019-08-29 21:27:33
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

	IPage<ShoppingCart> selectPage2(IPage<ShoppingCart> page, @Param("query") ShoppingCart shoppingCart);
}
