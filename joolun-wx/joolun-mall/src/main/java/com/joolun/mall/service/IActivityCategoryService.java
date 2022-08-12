package com.joolun.mall.service;

import com.joolun.mall.entity.ActivityCategory;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author Owen
 * @date 2022-08-12
 */
public interface IActivityCategoryService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public ActivityCategory selectActivityCategoryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param activityCategory 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ActivityCategory> selectActivityCategoryList(ActivityCategory activityCategory);

    /**
     * 新增【请填写功能名称】
     * 
     * @param activityCategory 【请填写功能名称】
     * @return 结果
     */
    public int insertActivityCategory(ActivityCategory activityCategory);

    /**
     * 修改【请填写功能名称】
     * 
     * @param activityCategory 【请填写功能名称】
     * @return 结果
     */
    public int updateActivityCategory(ActivityCategory activityCategory);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteActivityCategoryByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteActivityCategoryById(Long id);
}
