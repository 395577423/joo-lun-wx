package com.joolun.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.UserAudio;

import java.util.List;

/**
 * 用户书籍录音Service接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface IUserAudioService extends IService<UserAudio> {
    List<UserAudio> listUserAudio(String userId,Long courseId);

}
