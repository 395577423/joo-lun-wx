package com.joolun.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.common.core.domain.entity.SysUser;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.mall.mapper.UserShareRecordMapper;
import com.joolun.mall.service.IUserShareRecordService;
import com.joolun.system.service.ISysUserService;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    WxUserService wxUserService;

    /**
     * 新增用户分享记录信息
     *
     * @param userId
     * @param shareUserId
     */
    @Override
    public void addShareRecord(String userId, String shareUserId) {
        //判断当前用户是否已经存在分享记录
        List<UserShareRecord> shareRecords = this.list(Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getUserId, userId));
        if (CollectionUtil.isEmpty(shareRecords)) {
            WxUser wxUser = wxUserService.getById(userId);
            WxUser shareUser = wxUserService.getById(shareUserId);
            UserShareRecord userShareRecord = new UserShareRecord();
            userShareRecord.setUserId(String.valueOf(wxUser.getId()));
            userShareRecord.setUserName(wxUser.getNickName());
            userShareRecord.setParentUserId(shareUserId);
            userShareRecord.setParentUserName(shareUser.getNickName());
            userShareRecord.setUserHeadImg(shareUser.getHeadimgUrl());
            userShareRecord.setCreateTime(new Date());
            boolean result = save(userShareRecord);
            log.info("新增分享记录结果:{}", result);
            setMemberLevel(shareUser);
        }

    }


    private void setMemberLevel(WxUser shareUser) {
        List<UserShareRecord> shareRecords = this.list(Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getUserId, shareUser.getId()));
        if (shareRecords != null && shareRecords.size() >= 20) {
            shareUser.setSVip(true);
            wxUserService.updateById(shareUser);
        }
    }



    /**
     * 列表显示或作伙伴
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserShareRecord> listPartner(String userId) {
        LambdaQueryWrapper<UserShareRecord> queryWrapper = Wrappers.<UserShareRecord>lambdaQuery()
                .eq(UserShareRecord::getParentUserId, userId)
                .orderByDesc(UserShareRecord::getCreateTime);
        List<UserShareRecord> userShareRecords = list(queryWrapper);
        return userShareRecords;
    }

}
