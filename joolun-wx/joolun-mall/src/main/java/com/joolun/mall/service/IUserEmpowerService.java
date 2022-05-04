package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.EmpowerVideo;
import com.joolun.mall.entity.UserEmpower;

/**
 * 用户书籍录音Service接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface IUserEmpowerService extends IService<UserEmpower> {

    EmpowerVideo getOneById(String userId, Long id);

}
