package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 【请填写功能名称】对象 user_member_order
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public class UserMemberOrder extends Model<UserMemberOrder>
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;


    private String orderName;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;


    /** 支付方式1、货到付款；2、在线支付 */
    @Excel(name = "支付方式1、货到付款；2、在线支付")
    private String paymentWay;


    /** 是否支付0、未支付 1、已支付 */
    @Excel(name = "是否支付0、未支付 1、已支付")
    private String isPay;


    /** 支付价格 */
    @Excel(name = "支付价格")
    private BigDecimal paymentPrice;


    /** 付款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "付款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paymentTime;


    /**
     * 支付交易ID
     */
    @Excel(name = "支付交易ID")
    private String transactionId;


    /** 备注 */
    @Excel(name = "备注")
    private String remark;


    /** 用户id */
    @Excel(name = "用户id")
    private String userId;


    /** 创建时间 */
    private Date createTime;

    private Date updateTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public void setPaymentWay(String paymentWay)
    {
        this.paymentWay = paymentWay;
    }

    public String getPaymentWay() 
    {
        return paymentWay;
    }
    public void setIsPay(String isPay) 
    {
        this.isPay = isPay;
    }

    public String getIsPay() 
    {
        return isPay;
    }
    public void setPaymentPrice(BigDecimal paymentPrice) 
    {
        this.paymentPrice = paymentPrice;
    }

    public BigDecimal getPaymentPrice() 
    {
        return paymentPrice;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
