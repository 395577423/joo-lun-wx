package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

/**
 * 用户提现记录对象 user_withdraw_record
 * 
 * @author Lanjian
 * @date 2022-10-16
 */
public class UserWithdrawRecord extends Model<UserWithdrawRecord>
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    private Long id;


    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal amount;


    /** 银行账号 */
    @Excel(name = "银行账号")
    private String bankAccountNo;


    /** 开户行名称 */
    @Excel(name = "开户行名称")
    private String bankName;


    /** 是否已完成 */
    @Excel(name = "是否已完成")
    private Integer completed;


    /** 用户id */
    @Excel(name = "用户id")
    private String wxUserId;


    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;


    /** 体现完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "体现完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completedTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setBankAccountNo(String bankAccountNo) 
    {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountNo() 
    {
        return bankAccountNo;
    }
    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }
    public void setCompleted(Integer completed) 
    {
        this.completed = completed;
    }

    public Integer getCompleted() 
    {
        return completed;
    }
    public void setWxUserId(String wxUserId) 
    {
        this.wxUserId = wxUserId;
    }

    public String getWxUserId() 
    {
        return wxUserId;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setCompletedTime(Date completedTime) 
    {
        this.completedTime = completedTime;
    }

    public Date getCompletedTime() 
    {
        return completedTime;
    }


}
