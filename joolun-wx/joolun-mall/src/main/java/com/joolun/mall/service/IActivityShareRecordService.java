package com.joolun.mall.service;

import com.joolun.mall.entity.ActivityShareRecord;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author Owen
 * @date 2022-08-12
 */
public interface IActivityShareRecordService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ActivityShareRecord selectActivityShareRecordById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param activityShareRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ActivityShareRecord> selectActivityShareRecordList(ActivityShareRecord activityShareRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param activityShareRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertActivityShareRecord(ActivityShareRecord activityShareRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param activityShareRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateActivityShareRecord(ActivityShareRecord activityShareRecord);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteActivityShareRecordByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteActivityShareRecordById(Long id);
}
