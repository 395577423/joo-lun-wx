package com.joolun.mall.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 user_commission
 * 
 * @author Lanjian
 * @date 2022-09-05
 */
public class UserCommission extends Model<UserCommission>
{
    private static final long serialVersionUID = 1L;


    /** 主键id */
    private Long id;


    /** 佣金总金额 */
    @Excel(name = "佣金总金额")
    private BigDecimal totalAmount;



    /** 用户id */
    @Excel(name = "用户id")
    private String userId;


    /** 已完成返佣金额 */
    @Excel(name = "已完成返佣金额")
    private BigDecimal completedAmount;


    /** 佣金已提取金额 */
    @Excel(name = "佣金已提取金额")
    private BigDecimal withdrawAmount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setCompletedAmount(BigDecimal completedAmount) 
    {
        this.completedAmount = completedAmount;
    }

    public BigDecimal getCompletedAmount() 
    {
        return completedAmount;
    }
    public void setWithdrawAmount(BigDecimal withdrawAmount) 
    {
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getWithdrawAmount() 
    {
        return withdrawAmount;
    }


}
