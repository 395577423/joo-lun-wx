package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.weixin.entity.WxUser;

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
     * @param userId
     * @param shareUserId
     */
    void addShareRecord(String userId, String shareUserId);
}
