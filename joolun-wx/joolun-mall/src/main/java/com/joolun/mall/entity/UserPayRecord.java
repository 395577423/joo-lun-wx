package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;

/**
 * 【请填写功能名称】对象 user_pay_record
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public class UserPayRecord extends Model<UserPayRecord>
{
    private static final long serialVersionUID = 1L;


    /** 主键 */
    private Long id;


    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal paymentAmount;


    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;


    /** 支付用户id */
    @Excel(name = "支付用户id")
    private String userId;


    /** 支付业务类型 1购买会员 、2购买课程、3参与活动、4、购买商品 */
    @Excel(name = "支付业务类型 1购买会员 、2购买课程、3参与活动、4、购买商品")
    private Integer paymentType;


    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPaymentAmount(BigDecimal paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() 
    {
        return paymentAmount;
    }
    public void setPaymentTime(Date paymentTime) 
    {
        this.paymentTime = paymentTime;
    }

    public Date getPaymentTime() 
    {
        return paymentTime;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setPaymentType(Integer paymentType)
    {
        this.paymentType = paymentType;
    }

    public Integer getPaymentType()
    {
        return paymentType;
    }

}
