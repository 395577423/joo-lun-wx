package com.joolun.mall.service.impl;

import java.util.List;
import com.joolun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joolun.system.mapper.ActivityMapper;
import com.joolun.system.domain.Activity;
import com.joolun.system.service.IActivityService;

/**
 * 社会活动Service业务层处理
 * 
 * @author Owen
 * @date 2022-08-12
 */
@Service
public class ActivityServiceImpl implements IActivityService 
{
    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 查询社会活动
     * 
     * @param id 社会活动ID
     * @return 社会活动
     */
    @Override
    public Activity selectActivityById(Long id)
    {
        return activityMapper.selectActivityById(id);
    }

    /**
     * 查询社会活动列表
     * 
     * @param activity 社会活动
     * @return 社会活动
     */
    @Override
    public List<Activity> selectActivityList(Activity activity)
    {
        return activityMapper.selectActivityList(activity);
    }

    /**
     * 新增社会活动
     * 
     * @param activity 社会活动
     * @return 结果
     */
    @Override
    public int insertActivity(Activity activity)
    {
        activity.setCreateTime(DateUtils.getNowDate());
        return activityMapper.insertActivity(activity);
    }

    /**
     * 修改社会活动
     * 
     * @param activity 社会活动
     * @return 结果
     */
    @Override
    public int updateActivity(Activity activity)
    {
        return activityMapper.updateActivity(activity);
    }

    /**
     * 批量删除社会活动
     * 
     * @param ids 需要删除的社会活动ID
     * @return 结果
     */
    @Override
    public int deleteActivityByIds(Long[] ids)
    {
        return activityMapper.deleteActivityByIds(ids);
    }

    /**
     * 删除社会活动信息
     * 
     * @param id 社会活动ID
     * @return 结果
     */
    @Override
    public int deleteActivityById(Long id)
    {
        return activityMapper.deleteActivityById(id);
    }
}
