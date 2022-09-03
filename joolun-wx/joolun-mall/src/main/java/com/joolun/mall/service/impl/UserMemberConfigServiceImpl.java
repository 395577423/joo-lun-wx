package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.UserMemberConfig;
import com.joolun.mall.mapper.UserMemberConfigMapper;
import com.joolun.mall.service.IUserMemberConfigService;
import org.springframework.stereotype.Service;

/**
 * 会员价格设置Service业务层处理
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
@Service
public class UserMemberConfigServiceImpl extends ServiceImpl<UserMemberConfigMapper, UserMemberConfig> implements IUserMemberConfigService
{

}
