package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.EmpowerVideo;
import com.joolun.mall.entity.UserEmpower;
import com.joolun.mall.mapper.UserEmpowerMapper;
import com.joolun.mall.service.IUserEmpowerService;
import org.springframework.stereotype.Service;

/**
 * @author Owen
 * @date 2022/4/25 23:23
 */
@Service
public class UserEmpowerServiceImpl extends ServiceImpl<UserEmpowerMapper, UserEmpower> implements IUserEmpowerService {

    @Override
    public EmpowerVideo getOneById(String userId, Long id) {
        return getBaseMapper().getOneById(userId, id);
    }
}
