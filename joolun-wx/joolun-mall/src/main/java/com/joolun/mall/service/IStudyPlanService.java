package com.joolun.mall.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.StudyPlan;

/**
 * 学习奖励计划Service接口
 *
 * @author www.joolun.com
 * @date 2021-11-30
 */
public interface IStudyPlanService extends IService<StudyPlan> {
    /**
     * 查询学习奖励计划
     *
     * @param id 学习奖励计划ID
     * @return 学习奖励计划
     */
    StudyPlan selectStudyPlanById(Long id);

    /**
     * 查询学习奖励计划列表
     *
     * @param studyPlan 学习奖励计划
     * @return 学习奖励计划集合
     */
    List<StudyPlan> selectStudyPlanList(StudyPlan studyPlan);

    /**
     * 新增学习奖励计划
     *
     * @param studyPlan 学习奖励计划
     * @return 结果
     */
    int insertStudyPlan(StudyPlan studyPlan);

    /**
     * 修改学习奖励计划
     *
     * @param studyPlan 学习奖励计划
     * @return 结果
     */
    int updateStudyPlan(StudyPlan studyPlan);

    /**
     * 批量删除学习奖励计划
     *
     * @param ids 需要删除的学习奖励计划ID
     * @return 结果
     */
    int deleteStudyPlanByIds(Long[] ids);

    /**
     * 删除学习奖励计划信息
     *
     * @param id 学习奖励计划ID
     * @return 结果
     */
    int deleteStudyPlanById(Long id);
}
