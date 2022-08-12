package com.joolun.mall.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;

/**
 * 【请填写功能名称】对象 activity_share_record
 * 
 * @author Owen
 * @date 2022-08-12
 */
public class ActivityShareRecord extends Model<ActivityShareRecord>
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 佣金金额 */
    @Excel(name = "佣金金额")
    private BigDecimal commission;

    /** 分享活动id */
    @Excel(name = "分享活动id")
    private Long activityId;

    /** 分享活动名称 */
    @Excel(name = "分享活动名称")
    private String activityName;

    /** 分享者id */
    @Excel(name = "分享者id")
    private Long sharerId;

    /** 分享人 */
    @Excel(name = "分享人")
    private String sharer;

    /** 是否已提取 */
    @Excel(name = "是否已提取")
    private Integer withdraw;


}
