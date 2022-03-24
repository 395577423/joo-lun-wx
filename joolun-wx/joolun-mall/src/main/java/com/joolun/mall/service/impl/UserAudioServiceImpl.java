package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.UserAudio;
import com.joolun.mall.mapper.UserAudioMapper;
import com.joolun.mall.service.IUserAudioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户书籍录音Service业务层处理
 *
 * @author Owen
 * @date 2021-12-08
 */
@Service
public class UserAudioServiceImpl extends ServiceImpl<UserAudioMapper, UserAudio> implements IUserAudioService {


    @Override
    public List<UserAudio> listUserAudio(String userId, Long courseId) {
        return baseMapper.listUserAudio(userId, courseId);
    }
}
