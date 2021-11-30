package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.common.utils.DateUtils;
import com.joolun.mall.entity.StudyPlan;
import com.joolun.mall.mapper.StudyPlanMapper;
import com.joolun.mall.service.IStudyPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学习奖励计划Service业务层处理
 *
 * @author www.joolun.com
 * @date 2021-11-30
 */
@Service
public class StudyPlanServiceImpl extends ServiceImpl<StudyPlanMapper, StudyPlan> implements IStudyPlanService {

    /**
     * 查询学习奖励计划
     *
     * @param id 学习奖励计划ID
     * @return 学习奖励计划
     */
    @Override
    public StudyPlan selectStudyPlanById(Long id) {
        return baseMapper.selectStudyPlanById(id);
    }

    /**
     * 查询学习奖励计划列表
     *
     * @param studyPlan 学习奖励计划
     * @return 学习奖励计划
     */
    @Override
    public List<StudyPlan> selectStudyPlanList(StudyPlan studyPlan) {
        return baseMapper.selectStudyPlanList(studyPlan);
    }

    /**
     * 新增学习奖励计划
     *
     * @param studyPlan 学习奖励计划
     * @return 结果
     */
    @Override
    public int insertStudyPlan(StudyPlan studyPlan) {
        studyPlan.setCreateTime(DateUtils.getNowDate());
        return baseMapper.insertStudyPlan(studyPlan);
    }

    /**
     * 修改学习奖励计划
     *
     * @param studyPlan 学习奖励计划
     * @return 结果
     */
    @Override
    public int updateStudyPlan(StudyPlan studyPlan) {
        studyPlan.setUpdateTime(DateUtils.getNowDate());
        return baseMapper.updateStudyPlan(studyPlan);
    }

    /**
     * 批量删除学习奖励计划
     *
     * @param ids 需要删除的学习奖励计划ID
     * @return 结果
     */
    @Override
    public int deleteStudyPlanByIds(Long[] ids) {
        return baseMapper.deleteStudyPlanByIds(ids);
    }

    /**
     * 删除学习奖励计划信息
     *
     * @param id 学习奖励计划ID
     * @return 结果
     */
    @Override
    public int deleteStudyPlanById(Long id) {
        return baseMapper.deleteStudyPlanById(id);
    }
}
