package com.joolun.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.mall.mapper.UserShareRecordMapper;
import com.joolun.mall.service.IUserShareRecordService;
import com.joolun.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lanjian
 * @date 2022-09-03
 */
@Service
@Slf4j
public class UserShareRecordServiceImpl extends ServiceImpl<UserShareRecordMapper, UserShareRecord>
        implements IUserShareRecordService {

    @Autowired
    ISysUserService sysUserService;

    /**
     * 新增用户分享记录信息
     *
     * @param currUser
     * @param shareUserId
     */
    @Override
    public void addShareRecord(SysUser currUser, String shareUserId) {

        //判断当前用户是否已经存在分享记录
        List<UserShareRecord> shareRecords = this.list(Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getUserId, currUser.getUserId()));
        if (CollectionUtil.isEmpty(shareRecords)) {
            SysUser shareUser = sysUserService.selectUserById(Long.valueOf(shareUserId));
            UserShareRecord userShareRecord = new UserShareRecord();
            userShareRecord.setUserId(String.valueOf(currUser.getUserId()));
            userShareRecord.setUserName(currUser.getUserName());
            userShareRecord.setUserId(shareUserId);
            userShareRecord.setUserName(shareUser.getUserName());
            boolean result = save(userShareRecord);
            log.info("新增分享记录结果:{}" , result);
        }

    }
}
