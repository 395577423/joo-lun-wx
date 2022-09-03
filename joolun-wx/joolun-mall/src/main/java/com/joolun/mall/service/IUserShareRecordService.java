package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.mall.entity.UserShareRecord;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public interface IUserShareRecordService extends IService<UserShareRecord>
{

    /**
     * 新增用户分享记录信息
     * @param currUser
     * @param shareUserId
     */
    void addShareRecord(SysUser currUser,String shareUserId);
}
