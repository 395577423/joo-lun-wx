package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.entity.UserAudio;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户书籍录音Mapper接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface UserAudioMapper extends BaseMapper<UserAudio> {

    @Select("select a.*,b.question,b.audio as course_url from user_audio a ,course_audio b where a.course_id = b.course_id and a.audio_id = b.id and a.course_id = #{courseId} and a.user_id = #{userId}")
    List<UserAudio> listUserAudio(@Param("userId") String userId, @Param("courseId") Long courseId);
}
