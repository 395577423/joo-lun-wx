package com.joolun.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joolun.mall.entity.UserCommission;
import com.joolun.mall.entity.UserIncomeRecord;
import com.joolun.mall.enums.IncomeStatusEnum;
import com.joolun.mall.mapper.UserCommissionMapper;
import com.joolun.mall.service.IUserCommissionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lanjian
 * @date 2022-09-05
 */
@Service public class UserCommissionServiceImpl extends ServiceImpl<UserCommissionMapper, UserCommission>
        implements IUserCommissionService {

    /**
     * 更新佣金数据
     *
     * @param incomeRecord
     * @param status
     */
    @Override
    public void updateCommissionIncomeData(UserIncomeRecord incomeRecord, IncomeStatusEnum status) {
        if (incomeRecord != null && incomeRecord.getAmount() != null) {
            String userId = incomeRecord.getUserId();
            BigDecimal amount = incomeRecord.getAmount();
            LambdaQueryWrapper<UserCommission> queryWrapper =
                    Wrappers.<UserCommission>lambdaQuery().eq(UserCommission::getUserId, userId);
            UserCommission userCommission = getOne(queryWrapper);
            if (userCommission == null) {
                userCommission = new UserCommission();
                userCommission.setUserId(userId);
                userCommission.setTotalAmount(amount);
                userCommission.setCompletedAmount(IncomeStatusEnum.COMPLETED == status ? amount : BigDecimal.ZERO);
                userCommission.setWithdrawAmount(BigDecimal.ZERO);
            } else {
                userCommission.setTotalAmount(IncomeStatusEnum.COMPLETED == status ?
                        userCommission.getTotalAmount() :
                        amount.add(userCommission.getTotalAmount()));
                userCommission.setCompletedAmount(IncomeStatusEnum.COMPLETED == status ?
                        amount.add(userCommission.getCompletedAmount()) :
                        userCommission.getCompletedAmount());
            }
            saveOrUpdate(userCommission);
        }
    }

}
