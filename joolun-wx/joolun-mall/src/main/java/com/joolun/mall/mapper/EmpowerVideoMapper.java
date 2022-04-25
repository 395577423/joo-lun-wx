package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joolun.mall.entity.EmpowerVideo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Owen
 * @date 2022/4/16 21:21
 */
public interface EmpowerVideoMapper extends BaseMapper<EmpowerVideo> {

    @Select("<script>" +
            "select a.id, title, url, video_level, price, rates, introduction, create_time,(select b.user_id " +
            "from user_empower b where b.user_id = #{userId} and b.empower_id = a.id) user_id from empower_video a " +
            "where 1=1 " +
            " <if test=\"name != null and name != '' \" >" +
            "and  a.title like  CONCAT( '%',#{name},'%') " +
            "</if>"+
            "</script>")
    IPage<EmpowerVideo> selectPage2(Page page, @Param("name") String name, @Param("userId") String userId);
}
