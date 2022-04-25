package com.joolun.mall.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.joolun.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户购买课程对象 user_course
 *
 * @author Owen
 * @date 2021-12-08
 */
@Data
@TableName("user_course")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户购买课程对象")
public class UserCourse extends Model<UserCourse> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 课程ID
     */
    @Excel(name = "课程ID")
    private Long courseId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private String userId;

    /**
     * 得到的返现金额
     */
    @Excel(name = "得到的返现金额")
    private BigDecimal cashReturn;

    /**
     * 购买价格
     */
    @Excel(name = "购买价格")
    private BigDecimal price;

    /**
     * 是否有返现权限
     */
    @Excel(name = "是否有返现权限 1:有 0:无")
    private Long returnable;

    /**
     * 无返现原因
     */
    @Excel(name = "无返现原因")
    private String reason;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户读书报告图片地址
     */
    private String report;

    /**
     * 是否奖学金计划内购买
     */
    private String isPlanCourse;

    /**
     * 奖学金应返金额
     */
    private BigDecimal originalMoney;
}
