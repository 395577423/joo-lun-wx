package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;

/**
 * 会员价格设置对象 user_member_config
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public class UserMemberConfig extends Model<UserMemberConfig>
{
    private static final long serialVersionUID = 1L;


    /** 主键 */
    private Long id;


    /** 会员价格 */
    @Excel(name = "会员价格")
    private BigDecimal price;


    /** 普通会员返现价格 */
    @Excel(name = "普通会员返现价格")
    private BigDecimal cashBackAmount;


    /** 普通会员返现价格 */
    @Excel(name = "普通会员返现价格")
    private BigDecimal superCashBackAmount;


    /** 创建人id */
    @Excel(name = "创建人id")
    private String createId;


    /** 创建时间 */
    private Date createTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setCashBackAmount(BigDecimal cashBackAmount) 
    {
        this.cashBackAmount = cashBackAmount;
    }

    public BigDecimal getCashBackAmount() 
    {
        return cashBackAmount;
    }
    public void setSuperCashBackAmount(BigDecimal superCashBackAmount) 
    {
        this.superCashBackAmount = superCashBackAmount;
    }

    public BigDecimal getSuperCashBackAmount() 
    {
        return superCashBackAmount;
    }
    public void setCreateId(String createId) 
    {
        this.createId = createId;
    }

    public String getCreateId() 
    {
        return createId;
    }

}
