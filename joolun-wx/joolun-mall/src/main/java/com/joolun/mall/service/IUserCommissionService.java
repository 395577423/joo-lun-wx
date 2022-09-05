package com.joolun.mall.service;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joolun.mall.entity.UserCommission;
import com.joolun.mall.entity.UserIncomeRecord;
import com.joolun.mall.enums.IncomeStatusEnum;

/**
 * 【请填写功能名称】Service接口
 *
 * @author Lanjian
 * @date 2022-09-05
 */
public interface IUserCommissionService extends IService<UserCommission> {

    /**
     * 更新佣金数据
     * @param incomeRecord
     * @param status
     */
    void updateCommissionIncomeData(UserIncomeRecord incomeRecord, IncomeStatusEnum status);

}
