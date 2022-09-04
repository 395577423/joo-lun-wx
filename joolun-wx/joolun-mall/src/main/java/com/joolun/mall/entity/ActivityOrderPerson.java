package com.joolun.mall.entity;

import com.joolun.common.annotation.Excel;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

/**
 * 【请填写功能名称】对象 activity_order_person
 * 
 * @author Lanjian
 * @date 2022-09-03
 */
public class ActivityOrderPerson extends Model<ActivityOrderPerson>
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    private Long id;


    /** 订单id */
    @Excel(name = "订单id")
    private String orderId;


    /** 出行人id */
    @Excel(name = "出行人id")
    private Long personId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setPersonId(Long personId) 
    {
        this.personId = personId;
    }

    public Long getPersonId() 
    {
        return personId;
    }


}
