package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 社会活动对象 activity
 * 
 * @author Owen
 * @date 2022-08-12
 */
@Data
public class Activity extends Model<Activity>
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 活动分类id */
    @Excel(name = "活动分类id")
    private Long categoryId;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String name;

    /** 活动价格 */
    @Excel(name = "活动价格")
    private Long price;

    /** 活动地点 */
    @Excel(name = "活动地点")
    private String address;

    /** $column.columnComment */
    @Excel(name = "活动地点")
    private String imageUrl;

    /** 活动位置信息 */
    @Excel(name = "活动位置信息")
    private String location;

    /** 活动介绍 */
    @Excel(name = "活动介绍")
    private String introduction;

    /** 活动说明 */
    @Excel(name = "活动说明")
    private String explanation;

    /** 佣金 */
    @Excel(name = "佣金")
    private BigDecimal commission;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long creatorId;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String creator;

    private Date createTime;

}
