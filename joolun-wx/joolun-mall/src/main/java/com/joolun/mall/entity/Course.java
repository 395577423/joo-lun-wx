package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 课程对象 course
 * 
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("course")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "课程对象")
public class Course extends Model<Course>
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 介绍 */
    @Excel(name = "介绍")
    private String introduction;

    /** 课程图片地址 */
    @Excel(name = "课程图片地址")
    private String coverUrl;

    /** 1 推荐 0 不推荐 */
    @Excel(name = "1 推荐 0 不推荐")
    private String recommend;

    /** 1 正常 0 停用 */
    @Excel(name = "1 正常 0 停用")
    private String status;

    /** 课程开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "课程开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 课程结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "课程结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 适应开始年龄 */
    @Excel(name = "适应开始年龄")
    private Long ageStart;

    /** 适应结束年龄 */
    @Excel(name = "适应结束年龄")
    private Long ageEnd;

    /** 课程价格 */
    @Excel(name = "课程价格")
    private BigDecimal price;

    /** 返现金额 */
    @Excel(name = "返现金额")
    private BigDecimal cashReturn;

    /** 折扣价 */
    @Excel(name = "折扣价")
    private BigDecimal rates;

    /** 是否参与奖学金计划 1 参加 0 不参加 */
    @Excel(name = "是否参与奖学金计划 1 参加 0 不参加")
    private String plan;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 最后更新时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 创建者
     */
    private String createBy;
    /**
     * 备注
     */
    private String remark;

    /**
     * 销量
     */
    private Integer saleNum;

    /**
     * 书籍精华
     */
    private String essence;

    /**
     * 参与者
     */
    private Integer participant;

    /**
     * 关联书籍
     */
    @TableField(exist = false)
    private List<Long> books;

    /**
     * 关联书籍名
     */
    @TableField(exist = false)
    private List<String> bookNames;

    @TableField(exist = false)
    private List<Book> bookList;
}
