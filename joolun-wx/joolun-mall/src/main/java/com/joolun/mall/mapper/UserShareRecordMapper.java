package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.dto.PartnerVo;
import com.joolun.mall.dto.StoreDataVo;
import com.joolun.mall.entity.UserShareRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public interface UserShareRecordMapper extends BaseMapper<UserShareRecord>
{

    /**
     *  连接查询
     * @param userId
     * @return
     */
    @Select("select t2.headimg_url as userHeadImg,t2.nick_name as userName,t1.create_time,t2.vip,t2.s_vip " +
            "FROM user_share_record t1 left join wx_user t2 on t1.user_id=t2.id where t1.parent_user_id=#{userId} " +
            " order by t2.create_time desc")
    List<PartnerVo> selectJoinWxUser(@Param("userId") String userId);


    /**
     * 查询店铺数据
     * @param nickName
     * @return
     */
    @Select({"<script>"+" select t1.*,t2.total_amount,t2.completed_amount,t2.withdraw_amount from (select wx_user.id, " +
            "wx_user.nick_name as nickName,count(1) as subCount,COUNT(DISTINCT CASE WHEN wx_user.vip = '1' THEN 1 END) " +
            "as memberCount  from user_share_record left JOIN wx_user on user_share_record.parent_user_id=wx_user.id " +
            "GROUP BY wx_user.id,wx_user.nick_name order by count(1) DESC) t1 left join user_commission t2 " +
            "on t1.id=t2.user_id <where> <if test='nickName!= null and nickName !=\"\" '> t1.nickName like " +
            "concat('%',#{nickName},'%') </if></where> "+"</script>"})
    List<StoreDataVo> listStore(@Param("nickName") String nickName);
}
