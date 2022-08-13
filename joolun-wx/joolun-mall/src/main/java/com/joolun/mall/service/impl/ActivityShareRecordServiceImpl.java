package com.joolun.mall.service.impl;

import com.joolun.common.utils.DateUtils;
import com.joolun.mall.entity.ActivityShareRecord;
import com.joolun.mall.mapper.ActivityShareRecordMapper;
import com.joolun.mall.service.IActivityShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author Owen
 * @date 2022-08-12
 */
@Service
public class ActivityShareRecordServiceImpl implements IActivityShareRecordService
{
    @Autowired
    private ActivityShareRecordMapper activityShareRecordMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ActivityShareRecord selectActivityShareRecordById(Long id)
    {
        return activityShareRecordMapper.selectActivityShareRecordById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param activityShareRecord 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ActivityShareRecord> selectActivityShareRecordList(ActivityShareRecord activityShareRecord)
    {
        return activityShareRecordMapper.selectActivityShareRecordList(activityShareRecord);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param activityShareRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertActivityShareRecord(ActivityShareRecord activityShareRecord)
    {
        activityShareRecord.setCreateTime(DateUtils.getNowDate());
        return activityShareRecordMapper.insertActivityShareRecord(activityShareRecord);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param activityShareRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateActivityShareRecord(ActivityShareRecord activityShareRecord)
    {
        return activityShareRecordMapper.updateActivityShareRecord(activityShareRecord);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteActivityShareRecordByIds(Long[] ids)
    {
        return activityShareRecordMapper.deleteActivityShareRecordByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteActivityShareRecordById(Long id)
    {
        return activityShareRecordMapper.deleteActivityShareRecordById(id);
    }
}
