package com.joolun.mall.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;

/**
 * 【请填写功能名称】对象 user_commission
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public class UserCommission extends Model<UserCommission>
{
    private static final long serialVersionUID = 1L;


    /** 主键id */
    private Long id;


    /** 佣金总金额 */
    @Excel(name = "佣金总金额")
    private BigDecimal amountTotal;


    /** 佣金已提取金额 */
    @Excel(name = "佣金已提取金额")
    private BigDecimal amountWithdraw;


    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAmountTotal(BigDecimal amountTotal) 
    {
        this.amountTotal = amountTotal;
    }

    public BigDecimal getAmountTotal() 
    {
        return amountTotal;
    }
    public void setAmountWithdraw(BigDecimal amountWithdraw) 
    {
        this.amountWithdraw = amountWithdraw;
    }

    public BigDecimal getAmountWithdraw() 
    {
        return amountWithdraw;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }

}
