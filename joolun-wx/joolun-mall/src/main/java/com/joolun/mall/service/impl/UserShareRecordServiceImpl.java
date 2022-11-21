package com.joolun.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.joolun.mall.constant.MallConstants;
import com.joolun.mall.dto.PartnerVo;
import com.joolun.mall.dto.StoreDataVo;
import com.joolun.mall.entity.UserShareRecord;
import com.joolun.mall.mapper.UserShareRecordMapper;
import com.joolun.mall.service.IUserShareRecordService;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        if (CollectionUtil.isEmpty(shareRecords) && !userId.equals(shareUserId)) {
            WxUser wxUser = wxUserService.getById(userId);
            UserShareRecord userShareRecord = new UserShareRecord();
            userShareRecord.setUserId(String.valueOf(wxUser.getId()));
            userShareRecord.setParentUserId(shareUserId);
            userShareRecord.setCreateTime(new Date());
            boolean result = save(userShareRecord);
            log.info("新增分享记录结果:{}", result);
        }

    }


    /**
     * 列表显示或作伙伴
     *
     * @param userId
     * @return
     */
    @Override
    public List<PartnerVo> listPartner(String userId) {
        List<PartnerVo> partnerVos = this.getBaseMapper().selectJoinWxUser(userId);
        return partnerVos;
    }

    /**
     * 店铺数据
     *
     * @param nickName
     * @return
     */
    @Override
    public List<StoreDataVo> queryStoreData(String nickName) {
        return getBaseMapper().listStore(nickName);
    }

}
