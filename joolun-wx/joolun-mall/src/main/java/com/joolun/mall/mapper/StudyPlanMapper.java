package com.joolun.mall.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.entity.StudyPlan;

/**
 * 学习奖励计划Mapper接口
 * 
 * @author www.joolun.com
 * @date 2021-11-30
 */
public interface StudyPlanMapper extends BaseMapper<StudyPlan>
{
    /**
     * 查询学习奖励计划
     * 
     * @param id 学习奖励计划ID
     * @return 学习奖励计划
     */
    public StudyPlan selectStudyPlanById(Long id);

    /**
     * 查询学习奖励计划列表
     * 
     * @param studyPlan 学习奖励计划
     * @return 学习奖励计划集合
     */
    public List<StudyPlan> selectStudyPlanList(StudyPlan studyPlan);

    /**
     * 新增学习奖励计划
     * 
     * @param studyPlan 学习奖励计划
     * @return 结果
     */
    public int insertStudyPlan(StudyPlan studyPlan);

    /**
     * 修改学习奖励计划
     * 
     * @param studyPlan 学习奖励计划
     * @return 结果
     */
    public int updateStudyPlan(StudyPlan studyPlan);

    /**
     * 删除学习奖励计划
     * 
     * @param id 学习奖励计划ID
     * @return 结果
     */
    public int deleteStudyPlanById(Long id);

    /**
     * 批量删除学习奖励计划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStudyPlanByIds(Long[] ids);
}
