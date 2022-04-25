package com.joolun.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 赋能中心视频对象 empower_video
 * 
 * @author Owen
 * @date 2022-04-16
 */
@Data
@TableName("empower_video")
@EqualsAndHashCode(callSuper = true)
public class EmpowerVideo extends Model<EmpowerVideo>
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 视频地址 */
    @Excel(name = "视频地址")
    private String url;

    /** 初 中 高级 */
    @Excel(name = "初 中 高级")
    private Short videoLevel;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 折扣价 */
    @Excel(name = "折扣价")
    private BigDecimal rates;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    @Excel(name = "创建日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private String userId;

}