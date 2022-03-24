
package com.joolun.weixin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.weixin.entity.WxUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 微信用户
 *
 * @author Owen
 * @date 2019-03-25 15:39:39
 */
public interface WxUserMapper extends BaseMapper<WxUser> {

    @Select("update wx_user set money = #{user.money} where id = #{user.id}")
    void updateMoney(@Param("user") WxUser wxUser);
}
