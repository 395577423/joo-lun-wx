package com.joolun.mall.service.impl;

import java.util.List;
import com.joolun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joolun.system.mapper.ActivityCategoryMapper;
import com.joolun.system.domain.ActivityCategory;
import com.joolun.system.service.IActivityCategoryService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author Owen
 * @date 2022-08-12
 */
@Service
public class ActivityCategoryServiceImpl implements IActivityCategoryService 
{
    @Autowired
    private ActivityCategoryMapper activityCategoryMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public ActivityCategory selectActivityCategoryById(Long id)
    {
        return activityCategoryMapper.selectActivityCategoryById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param activityCategory 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ActivityCategory> selectActivityCategoryList(ActivityCategory activityCategory)
    {
        return activityCategoryMapper.selectActivityCategoryList(activityCategory);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param activityCategory 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertActivityCategory(ActivityCategory activityCategory)
    {
        activityCategory.setCreateTime(DateUtils.getNowDate());
        return activityCategoryMapper.insertActivityCategory(activityCategory);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param activityCategory 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateActivityCategory(ActivityCategory activityCategory)
    {
        return activityCategoryMapper.updateActivityCategory(activityCategory);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteActivityCategoryByIds(Long[] ids)
    {
        return activityCategoryMapper.deleteActivityCategoryByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteActivityCategoryById(Long id)
    {
        return activityCategoryMapper.deleteActivityCategoryById(id);
    }
}
