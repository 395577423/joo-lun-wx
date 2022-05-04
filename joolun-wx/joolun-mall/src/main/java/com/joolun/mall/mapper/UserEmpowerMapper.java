package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.entity.EmpowerVideo;
import com.joolun.mall.entity.UserEmpower;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户购买赋能中心Mapper接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface UserEmpowerMapper extends BaseMapper<UserEmpower> {
    @Select("select a.id, title, url, video_level, price, rates, introduction, create_time,cover_url,(select b.user_id " +
            "from user_empower b where b.user_id = #{userId} and b.empower_id = a.id) user_id from empower_video a where a.id = #{id}")
    EmpowerVideo getOneById(@Param("userId") String userId, @Param("id")Long id);

}
