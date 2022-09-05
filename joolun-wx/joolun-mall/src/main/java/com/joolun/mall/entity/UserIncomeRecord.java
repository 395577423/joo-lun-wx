package com.joolun.mall.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 【请填写功能名称】对象 user_income_record
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public class UserIncomeRecord extends Model<UserIncomeRecord>
{
    private static final long serialVersionUID = 1L;


    /** 主键 */
    private Long id;


    /** 收入金额 */
    @Excel(name = "收入金额")
    private BigDecimal amount;


    /** 收入来源类型 */
    @Excel(name = "收入来源类型")
    private Integer sourceType;


    /** 创建时间 */
    private Date createTime;


    /** 用户id */
    @Excel(name = "用户id")
    private String userId;


    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userNickName;


    /** 来源用户id */
    @Excel(name = "来源用户id")
    private String sourceUserId;


    /** 来源用户昵称 */
    @Excel(name = "来源用户昵称")
    private String sourceUserNickName;


    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 状态 0 返佣中 1 已返佣
     */
    private String status;

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
    public void setSourceType(Integer sourceType)
    {
        this.sourceType = sourceType;
    }

    public Integer getSourceType()
    {
        return sourceType;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getSourceUserId()
    {
        return sourceUserId;
    }

    public void setSourceUserId(String sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public void setSourceUserNickName(String sourceUserNickName)
    {
        this.sourceUserNickName = sourceUserNickName;
    }

    public String getSourceUserNickName() 
    {
        return sourceUserNickName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
