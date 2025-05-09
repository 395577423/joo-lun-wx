package com.joolun.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.dto.PartnerVo;
import com.joolun.mall.dto.StoreDataVo;
import com.joolun.mall.entity.UserShareRecord;

import java.util.List;

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


    /**
     * 列表显示或作伙伴
     * @param userId
     * @return
     */
    List<PartnerVo> listPartner(String userId);

    /**
     * 店铺数据
     * @param nickName
     * @return
     */
    List<StoreDataVo> queryStoreData(String nickName);


}
